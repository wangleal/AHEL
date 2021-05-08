package wang.leal.ahel.bus

import android.util.ArrayMap
import java.lang.Exception

class Request internal constructor(val action:String){
    private var params: ArrayMap<String, Any>? = null
    @Suppress("UNCHECKED_CAST")
    fun <T> getParam(key:String):T?{
        val data = params?.get(key)
        return try {
            data as T
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    @Synchronized
    internal fun putParam(key:String,value:Any?){
        if (params==null){
            params= ArrayMap()
        }
        params?.put(key,value)
    }
}