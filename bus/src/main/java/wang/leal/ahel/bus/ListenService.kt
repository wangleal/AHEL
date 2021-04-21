package wang.leal.ahel.bus

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

class ListenService(private val server:String) {

    private var isNeedLast = false
    fun needLast():ListenService {
        isNeedLast = true
        return this
    }

    fun <T> observable(clazz: Class<T>): Observable<T> {
        val observable: Observable<String> = if (isNeedLast) {
            SubjectFactory.behaviorSubject(server)
        } else {
            SubjectFactory.publishSubject(server)
        }
        return observable.map { json ->
            Converter.convert(json, clazz)
        }.subscribeOn(BusScheduler.scheduler())
                .observeOn(AndroidSchedulers.mainThread()).retry()
    }

}