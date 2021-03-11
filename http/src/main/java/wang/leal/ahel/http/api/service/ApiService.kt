package wang.leal.ahel.http.api.service

object ApiService {

    fun createService():CreateService{
        return CreateService
    }

    fun getService(key:String):GetService{
        return GetService(key)
    }

    fun postService(key:String):PostService{
        return PostService(key)
    }

    fun deleteService(key:String):DeleteService{
        return DeleteService(key)
    }

    fun patchService(key:String):PatchService{
        return PatchService(key)
    }

    fun putService(key:String):PutService{
        return PutService(key)
    }

}