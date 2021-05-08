package wang.leal.ahel.bus

import android.util.ArrayMap
import java.lang.Exception

class Event(val action: String){

    private var dataMap: ArrayMap<String, Any>? = null

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key:String):T?{
        val data = dataMap?.get(key)
        return try {
            data as T
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key:String,defaultValue:T):T{
        val data = dataMap?.get(key)
        return try {
            data?.let {
                data as T
            }?:defaultValue
        }catch (e:Exception){
            e.printStackTrace()
            defaultValue
        }
    }

    @Synchronized
    internal fun put(key:String,value:Any){
        if (dataMap==null){
            dataMap=ArrayMap()
        }
        dataMap?.put(key,value)
    }
}