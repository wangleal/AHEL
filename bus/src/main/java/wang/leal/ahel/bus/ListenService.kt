package wang.leal.ahel.bus

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

class ListenService(private vararg val servers:String) {

    private var isNeedLast = false
    fun needLast():ListenService {
        isNeedLast = true
        return this
    }

    fun <T> observable(clazz: Class<T>): Observable<Event<T>> {
        val subjects = mutableListOf<Observable<Event<String>>>()
        for (server:String in servers){
            subjects.add(subject(server))
        }
        return Observable.merge(subjects).map { event ->
            Event(event.action,Converter.convert(event.data, clazz))
        }.subscribeOn(BusScheduler.scheduler())
                .observeOn(AndroidSchedulers.mainThread()).retry()
    }

    private fun subject(server:String):Observable<Event<String>>{
        return if (isNeedLast) {
            SubjectFactory.behaviorSubject(server)
        } else {
            SubjectFactory.publishSubject(server)
        }.map {
            Event(server,it)
        }
    }

}