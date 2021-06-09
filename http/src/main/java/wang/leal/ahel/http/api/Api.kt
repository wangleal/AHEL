package wang.leal.ahel.http.api

import wang.leal.ahel.http.api.service.*

object Api {
    @JvmStatic
    fun <T> create(service: Class<T>):T{
        return ApiService.createService().create(service)
    }

    @JvmStatic
    fun get(url:String): GetService {
        return ApiService.getService(url)
    }

    @JvmStatic
    fun post(url:String): PostService {
        return ApiService.postService(url)
    }

    @JvmStatic
    fun delete(url:String):DeleteService{
        return ApiService.deleteService(url)
    }

    @JvmStatic
    fun patch(url:String):PatchService{
        return ApiService.patchService(url)
    }

    @JvmStatic
    fun put(url:String):PutService{
        return ApiService.putService(url)
    }

}