package wang.leal.ahel.storage.json

import com.google.gson.Gson

internal object GsonManager {
    private val gson = Gson()
    fun gson():Gson{
        return gson
    }
}