package wang.leal.ahel.bus

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

class ListenService(private vararg val actions:String) {

    private var isNeedLast = false

    fun accept(): Observable<Event> {
        return accept(false)
    }

    fun accept(isNeedLast:Boolean? = false): Observable<Event> {
        this.isNeedLast = isNeedLast?:false
        val subjects = mutableListOf<Observable<Event>>()
        for (action:String in actions){
            subjects.add(subject(action))
        }
        return Observable.merge(subjects)
                .subscribeOn(BusScheduler.scheduler())
                .observeOn(AndroidSchedulers.mainThread()).retry()
    }

    private fun subject(action:String):Observable<Event>{
        return if (isNeedLast) {
            SubjectFactory.behaviorSubject(action)
        } else {
            SubjectFactory.publishSubject(action)
        }
    }

}