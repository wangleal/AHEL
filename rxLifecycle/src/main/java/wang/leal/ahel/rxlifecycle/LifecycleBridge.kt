package wang.leal.ahel.rxlifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

internal class LifecycleBridge(private val lifecycleOwner: LifecycleOwner) : LifecycleObserver, LifecycleProvider{
    companion object {
        private val cacheMap = WeakHashMap<LifecycleOwner, LifecycleBridge>()
        fun get(owner: LifecycleOwner): LifecycleBridge = cacheMap.getOrPut(owner) { LifecycleBridge(owner) }
    }

    private val lifecycleSubject = BehaviorSubject.create<Lifecycle.Event>().toSerialized()

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    /**
     * 是否是销毁状态
     */
    private val isDestroy
        get() = lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED

    /**
     * @param source LifecycleOwner
     * @param event Lifecycle.Event
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onEvent(source: LifecycleOwner, event: Lifecycle.Event) {
        lifecycleSubject.onNext(event)
        if (isDestroy) release()
    }

    /**
     * 注销相关监听器
     */
    private fun release() {
        lifecycleSubject.onComplete()
        cacheMap.remove(lifecycleOwner)
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    override fun <T> bindToLifecycle() = LifecycleTransformer<T>(lifecycleSubject)

    override fun <T> bindUntilEvent(disposeEvent: Lifecycle.Event) = LifecycleTransformer<T>(lifecycleSubject, disposeEvent)
}