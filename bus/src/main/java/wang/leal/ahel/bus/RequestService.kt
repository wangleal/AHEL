package wang.leal.ahel.bus

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

class RequestService(private val server:String) {
    private var params:Any? = null
    fun params(params:Any): RequestService {
        this.params = params
        return this
    }

    fun <T> observable(clazz:Class<T>): Observable<T> {
        return Observable.create<T> {observer->
            try {
                CallFactory.get(server)
                        ?.execute(params?.let {
                            Converter.convert(it)
                        }?:"",object : Callback {
                            override fun call(result: Any?) {
                                result?.let {
                                    observer.onNext(Converter.convert(Converter.convert(result), clazz))
                                    observer.onComplete()
                                }?:observer.onError(RuntimeException("404: no result!"))
                            }
                        })?:observer.onError(RuntimeException("404: no such server!"))
            } catch (e: Exception) {
                observer.onError(e)
            }
        }.subscribeOn(BusScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }
}