package wang.leal.ahel.rxlifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.core.*

fun <T> Observable<T>.bindOnDestroy(owner: LifecycleOwner): Observable<T>{
    return this.compose(RxLifecycle.with(owner).bindOnDestroy<T>())
}
fun <T> Observable<T>.bindToLifecycle(owner: LifecycleOwner):Observable<T>{
    return this.compose(RxLifecycle.with(owner).bindToLifecycle())
}
fun <T> Observable<T>.bindUntilEvent(owner:LifecycleOwner,event: Lifecycle.Event):Observable<T>{
    return this.compose(RxLifecycle.with(owner).bindUntilEvent(event))
}

fun <T> Flowable<T>.bindOnDestroy(owner: LifecycleOwner): Flowable<T>{
    return this.compose(RxLifecycle.with(owner).bindOnDestroy())
}
fun <T> Flowable<T>.bindToLifecycle(owner: LifecycleOwner):Flowable<T>{
    return this.compose(RxLifecycle.with(owner).bindToLifecycle())
}
fun <T> Flowable<T>.bindUntilEvent(owner:LifecycleOwner,event: Lifecycle.Event):Flowable<T>{
    return this.compose(RxLifecycle.with(owner).bindUntilEvent(event))
}

fun <T> Maybe<T>.bindOnDestroy(owner: LifecycleOwner): Maybe<T>{
    return this.compose(RxLifecycle.with(owner).bindOnDestroy())
}
fun <T> Maybe<T>.bindToLifecycle(owner: LifecycleOwner):Maybe<T>{
    return this.compose(RxLifecycle.with(owner).bindToLifecycle())
}
fun <T> Maybe<T>.bindUntilEvent(owner:LifecycleOwner,event: Lifecycle.Event):Maybe<T>{
    return this.compose(RxLifecycle.with(owner).bindUntilEvent(event))
}

fun <T> Single<T>.bindOnDestroy(owner: LifecycleOwner): Single<T>{
    return this.compose(RxLifecycle.with(owner).bindOnDestroy())
}
fun <T> Single<T>.bindToLifecycle(owner: LifecycleOwner):Single<T>{
    return this.compose(RxLifecycle.with(owner).bindToLifecycle())
}
fun <T> Single<T>.bindUntilEvent(owner:LifecycleOwner,event: Lifecycle.Event):Single<T>{
    return this.compose(RxLifecycle.with(owner).bindUntilEvent(event))
}

fun Completable.bindOnDestroy(owner: LifecycleOwner): Completable{
    return this.compose(RxLifecycle.with(owner).bindOnDestroy<Any>())
}
fun Completable.bindToLifecycle(owner: LifecycleOwner):Completable{
    return this.compose(RxLifecycle.with(owner).bindToLifecycle<Any>())
}
fun Completable.bindUntilEvent(owner:LifecycleOwner,event: Lifecycle.Event):Completable{
    return this.compose(RxLifecycle.with(owner).bindUntilEvent<Any>(event))
}
