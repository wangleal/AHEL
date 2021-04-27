package wang.leal.ahel.bus

internal object BusService {

    fun listenService(vararg actions:String):ListenService{
        return ListenService(*actions)
    }

    fun postService(action:String):PostService{
        return PostService(action)
    }

    fun requestService(action:String):RequestService{
        return RequestService(action)
    }

    fun providerService(action: String):ProviderService{
        return ProviderService(action)
    }

}