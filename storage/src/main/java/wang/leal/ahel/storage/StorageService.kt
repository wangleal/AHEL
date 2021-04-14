package wang.leal.ahel.storage

import android.content.Context
import androidx.room.Room
import com.tencent.mmkv.MMKV
import wang.leal.ahel.storage.room.StorageDatabase
import java.io.File

internal object StorageService {
    private var storageDir:File? = null
    private var db:StorageDatabase? = null
    private var mmkvDir:String? = null
    fun initialize(context: Context){
        mmkvDir = MMKV.initialize(context)
        storageDir = File(context.filesDir, "storage")
        if (storageDir?.exists()==false){
            storageDir?.mkdirs()
        }
        db = Room.databaseBuilder(context, StorageDatabase::class.java, "storage.db").build()
    }

    fun kvService(name: String): KVService {
        if (mmkvDir==null){
            throw IllegalStateException("You should Call Storage.initialize() first.")
        }
        return KVService(name)
    }

    fun kvService(): KVService {
        if (mmkvDir==null){
            throw IllegalStateException("You should Call Storage.initialize() first.")
        }
        return KVService("storage")
    }

    fun fileService(key: String): FileService {
        if (storageDir==null){
            throw IllegalStateException("You should Call Storage.initialize() first.")
        }
        return FileService(key, storageDir)
    }

    fun roomService():RoomService{
        if (db==null){
            throw IllegalStateException("You should Call Storage.initialize() first.")
        }
        return RoomService(db)
    }
}