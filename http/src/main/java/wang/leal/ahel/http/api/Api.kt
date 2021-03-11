package wang.leal.ahel.http.api

import wang.leal.ahel.http.api.service.*

object Api {
    fun <T> create(service: Class<T>):T{
        return ApiService.createService().create(service)
    }

    fun get(key:String): GetService {
        return ApiService.getService(key)
    }

    fun post(key:String): PostService {
        return ApiService.postService(key)
    }

    fun delete(key:String):DeleteService{
        return ApiService.deleteService(key)
    }

    fun patch(key:String):PatchService{
        return ApiService.patchService(key)
    }

    fun put(key:String):PutService{
        return ApiService.putService(key)
    }

}