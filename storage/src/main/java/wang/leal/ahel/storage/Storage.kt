package wang.leal.ahel.storage

import android.content.Context

object Storage {

    fun initialize(context: Context){
        StorageService.initialize(context)
    }

    fun kv(name:String): KVService {
        return StorageService.kvService(name)
    }

    fun kv(): KVService {
        return StorageService.kvService()
    }

    fun file(key:String): FileService {
        return StorageService.fileService(key)
    }

    fun db():RoomService{
        return StorageService.roomService()
    }

}