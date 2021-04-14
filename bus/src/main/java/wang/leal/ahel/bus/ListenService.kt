package wang.leal.ahel.bus

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

class ListenService(private val server:String) {

    fun <T> observable(clazz:Class<T>): Observable<T> {
        return SubjectFactory.publishSubject(server)
            .map { json ->
                Converter.convert(json, clazz)
            }.subscribeOn(BusScheduler.scheduler())
            .observeOn(AndroidSchedulers.mainThread()).retry()
    }

}