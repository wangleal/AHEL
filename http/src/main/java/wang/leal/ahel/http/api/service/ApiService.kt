package wang.leal.ahel.http.api.service

object ApiService {

    fun createService():CreateService{
        return CreateService
    }

    fun getService(url:String):GetService{
        return GetService(url)
    }

    fun postService(url:String):PostService{
        return PostService(url)
    }

    fun deleteService(url:String):DeleteService{
        return DeleteService(url)
    }

    fun patchService(url:String):PatchService{
        return PatchService(url)
    }

    fun putService(url:String):PutService{
        return PutService(url)
    }

}