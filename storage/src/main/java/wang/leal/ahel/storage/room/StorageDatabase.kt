package wang.leal.ahel.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomKV::class], version = 1, exportSchema = false)
abstract class StorageDatabase :RoomDatabase(){
    abstract fun storageDao(): RoomKVDao
}