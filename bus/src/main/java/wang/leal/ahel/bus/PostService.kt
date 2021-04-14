package wang.leal.ahel.bus

import io.reactivex.rxjava3.core.Observable

class PostService(private val server:String) {

    fun execute(data:Any){
        Observable.create<Unit> {
            val json = Converter.convert(data)
            SubjectFactory.publishSubject(server)
                    .onNext(json)
        }.subscribeOn(BusScheduler.scheduler()).subscribe()
    }

}