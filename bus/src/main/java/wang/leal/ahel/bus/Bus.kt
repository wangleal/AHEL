package wang.leal.ahel.bus

object Bus {

    @JvmStatic
    fun listen(vararg actions:String):ListenService{
        return BusService.listenService(*actions)
    }

    @JvmStatic
    fun post(action:String):PostService{
        return BusService.postService(action)
    }

    @JvmStatic
    fun request(action:String):RequestService{
        return BusService.requestService(action)
    }

    @JvmStatic
    fun provide(action: String):ProviderService{
        return BusService.providerService(action)
    }

}