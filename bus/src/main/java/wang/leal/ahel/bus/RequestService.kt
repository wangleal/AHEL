package wang.leal.ahel.bus

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

class RequestService(private val action:String) {
    private var request:Request = Request(action)

    fun param(key:String,value:Any?):RequestService{
        value?.let {
            request.putParam(key,it)
        }
        return this
    }

    fun call(): Observable<Response> {
        return Observable.create<Response> {observer->
            try {
                CallFactory.get(action)
                        ?.execute(request,object :Callback{
                            override fun response(response: Response) {
                                observer.onNext(response)
                                observer.onComplete()
                            }
                        })?:observer.onError(RuntimeException("404: no such server!"))
            } catch (e: Exception) {
                observer.onError(e)
            }
        }.subscribeOn(BusScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }
}