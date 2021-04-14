package wang.leal.ahel.storage.room

import androidx.room.Entity

@Entity(primaryKeys = ["key"],tableName = "room_kv")
data class RoomKV(var key: String,
                  var value: String)