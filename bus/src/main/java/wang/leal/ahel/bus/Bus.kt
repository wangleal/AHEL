package wang.leal.ahel.bus

object Bus {

    fun listen(server:String): ListenService {
        return BusService.listenService(server)
    }

    fun post(server:String): PostService {
        return BusService.postService(server)
    }

    fun request(server:String): RequestService {
        return BusService.requestService(server)
    }

    fun provide(server: String): ProviderService {
        return BusService.providerService(server)
    }

}