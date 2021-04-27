package wang.leal.ahel.bus

import io.reactivex.rxjava3.core.Observable

class ProviderService(private val action:String) {
    fun call(call: Call){
        Observable.create<Unit> {
            CallFactory.put(action,call)
        }.subscribeOn(BusScheduler.scheduler()).subscribe()
    }
}