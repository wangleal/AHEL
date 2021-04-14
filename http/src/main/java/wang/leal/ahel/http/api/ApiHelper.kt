package wang.leal.ahel.http.api

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.observers.LambdaObserver

fun <T> Observable<T>.subscribe(observer: @NonNull ApiObserver<in T>): Disposable {
    val lambdaObserver = LambdaObserver<T>({
        observer.onNext(it)
    }, {
        observer.onError(it)
    }, {
        observer.onComplete()
    }, {
        observer.onSubscribe(it)
    })
    this.subscribe(lambdaObserver)
    return lambdaObserver
}