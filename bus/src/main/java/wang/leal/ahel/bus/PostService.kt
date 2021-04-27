package wang.leal.ahel.bus

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class PostService(private val action:String) {
    private var delay:Long = 0L
    fun delay(timestamp:Long):PostService{
        this.delay = timestamp
        return this
    }

    fun execute(data:Any?=null){
        Observable.create<Unit> {
            val json = data?.let { Converter.convert(data) }?:"{}"
            SubjectFactory.publishSubject(action)
                    .onNext(json)
            SubjectFactory.behaviorSubject(action)
                    .onNext(json)
        }.delay(delay,TimeUnit.MILLISECONDS).subscribeOn(BusScheduler.scheduler()).subscribe()
    }

}