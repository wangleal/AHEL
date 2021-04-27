package wang.leal.ahel.bus

object Bus {

    fun listen(vararg actions:String):ListenService{
        return BusService.listenService(*actions)
    }

    fun post(action:String):PostService{
        return BusService.postService(action)
    }

    fun request(action:String):RequestService{
        return BusService.requestService(action)
    }

    fun provide(action: String):ProviderService{
        return BusService.providerService(action)
    }

}