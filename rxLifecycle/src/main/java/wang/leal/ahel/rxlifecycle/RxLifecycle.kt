package wang.leal.ahel.rxlifecycle

import androidx.lifecycle.LifecycleOwner

object RxLifecycle {

    fun with(owner: LifecycleOwner): LifecycleProvider = LifecycleBridge.get(owner)
}