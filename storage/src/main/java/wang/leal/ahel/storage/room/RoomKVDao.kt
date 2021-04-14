package wang.leal.ahel.storage.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomKVDao {
    @Query("SELECT * FROM room_kv WHERE key = :key LIMIT 1")
    fun findByKey(key: String): RoomKV

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomKV: RoomKV)

    @Query("DELETE FROM room_kv WHERE key = :key")
    fun deleteByKey(key: String)
}