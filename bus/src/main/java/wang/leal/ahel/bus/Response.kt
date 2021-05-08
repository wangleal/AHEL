package wang.leal.ahel.bus

import java.lang.Exception

class Response internal constructor(val code:Int?,val message:String?,val data:Any?){

    fun code():Int?{
        return code
    }

    fun message():String?{
        return message
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> data():T?{
        return try {
            data as T
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}