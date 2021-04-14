package wang.leal.ahel.bus

internal object CallFactory {
    private val services = mutableMapOf<String, Call>()

    @Synchronized fun get(server:String): Call? {
        return services[server]
    }

    @Synchronized fun put(server:String, call: Call){
        services[server] = call
    }
}