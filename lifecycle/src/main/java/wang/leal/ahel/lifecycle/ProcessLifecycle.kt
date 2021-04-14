package wang.leal.ahel.lifecycle

import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object ProcessLifecycle:LifecycleObserver {

    private val subject = PublishSubject.create<Lifecycle.Event>().toSerialized()

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun observable(): Observable<Lifecycle.Event> {
        return subject.retry()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onEvent(source: LifecycleOwner, event: Lifecycle.Event) {
        subject.onNext(event)
    }

}