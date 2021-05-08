package wang.leal.ahel.bus

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class PostService(private val action:String) {
    private var delay:Long = 0L
    private val event = Event(action)
    fun delay(timestamp:Long):PostService{
        this.delay = timestamp
        return this
    }

    fun put(key:String,value:Any?):PostService{
        value?.let {
            event.put(key,it)
        }
        return this
    }

    fun send(){
        Observable.create<Unit> {
            SubjectFactory.publishSubject(action)
                    .onNext(event)
            SubjectFactory.behaviorSubject(action)
                    .onNext(event)
        }.delay(delay,TimeUnit.MILLISECONDS).subscribeOn(BusScheduler.scheduler()).subscribe()
    }

}