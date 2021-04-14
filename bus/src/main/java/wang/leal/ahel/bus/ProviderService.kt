package wang.leal.ahel.bus

import io.reactivex.rxjava3.core.Observable

class ProviderService(private val server:String) {
    fun call(call: Call){
        Observable.create<Unit> {
            CallFactory.put(server, call)
        }.subscribeOn(BusScheduler.scheduler()).subscribe()
    }
}