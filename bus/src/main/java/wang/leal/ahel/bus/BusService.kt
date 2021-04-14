package wang.leal.ahel.bus

internal object BusService {

    fun listenService(server:String): ListenService {
        return ListenService(server)
    }

    fun postService(server:String): PostService {
        return PostService(server)
    }

    fun requestService(server:String): RequestService {
        return RequestService(server)
    }

    fun providerService(server: String): ProviderService {
        return ProviderService(server)
    }

}