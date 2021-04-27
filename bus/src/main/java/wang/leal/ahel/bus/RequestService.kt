package wang.leal.ahel.bus

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

class RequestService(private val action:String) {
    private var params:Any? = null
    fun params(params:Any):RequestService{
        this.params = params
        return this
    }

    fun observable(): Observable<Event<String>> {
        return observable(String::class.java)
    }

    fun <T> observable(clazz:Class<T>): Observable<Event<T>> {
        return Observable.create<Event<T>> {observer->
            try {
                CallFactory.get(action)
                        ?.execute(params?.let {
                            Converter.convert(it)
                        }?:"",object :Callback{
                            override fun call(result: Any?) {
                                result?.let {
                                    observer.onNext(Event(action,Converter.convert(Converter.convert(result), clazz)))
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