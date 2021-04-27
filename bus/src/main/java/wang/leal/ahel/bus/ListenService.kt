package wang.leal.ahel.bus

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

class ListenService(private vararg val actions:String) {

    private var isNeedLast = false
    fun needLast():ListenService {
        isNeedLast = true
        return this
    }

    fun <T> observable(clazz: Class<T>): Observable<Event<T>> {
        val subjects = mutableListOf<Observable<Event<String>>>()
        for (action:String in actions){
            subjects.add(subject(action))
        }
        return Observable.merge(subjects).map { event ->
            Event(event.action,Converter.convert(event.data, clazz))
        }.subscribeOn(BusScheduler.scheduler())
                .observeOn(AndroidSchedulers.mainThread()).retry()
    }

    private fun subject(action:String):Observable<Event<String>>{
        return if (isNeedLast) {
            SubjectFactory.behaviorSubject(action)
        } else {
            SubjectFactory.publishSubject(action)
        }.map {
            Event(action,it)
        }
    }

}