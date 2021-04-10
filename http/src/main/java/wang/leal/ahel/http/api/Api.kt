package wang.leal.ahel.http.api

import wang.leal.ahel.http.api.service.*

object Api {
    fun <T> create(service: Class<T>):T{
        return ApiService.createService().create(service)
    }

    fun get(url:String): GetService {
        return ApiService.getService(url)
    }

    fun post(url:String): PostService {
        return ApiService.postService(url)
    }

    fun delete(url:String):DeleteService{
        return ApiService.deleteService(url)
    }

    fun patch(url:String):PatchService{
        return ApiService.patchService(url)
    }

    fun put(url:String):PutService{
        return ApiService.putService(url)
    }

}