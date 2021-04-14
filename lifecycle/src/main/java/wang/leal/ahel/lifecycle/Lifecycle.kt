package wang.leal.ahel.lifecycle

import android.app.Application

object Lifecycle {

    fun initialize(application: Application) {
        ProcessLifecycle
        ActivityLifecycle.initialize(application)
    }

}