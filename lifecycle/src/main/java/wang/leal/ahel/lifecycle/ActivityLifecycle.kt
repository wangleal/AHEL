package wang.leal.ahel.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.ref.WeakReference

object ActivityLifecycle {
    private val subject = PublishSubject.create<ActivityEvent>().toSerialized()
    private var weakActivity:WeakReference<Activity>? =  null
    internal fun initialize(application: Application) {
        registerActivityLifecycle(application)
    }

    fun observable(): Observable<ActivityEvent> {
        return subject
    }

    fun currentActivity():Activity? {
        return weakActivity?.get()
    }

    private fun registerActivityLifecycle(application: Application){
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                subject.onNext(ActivityEvent(activity,Lifecycle.Event.ON_CREATE))
            }

            override fun onActivityStarted(activity: Activity) {
                subject.onNext(ActivityEvent(activity,Lifecycle.Event.ON_START))
            }

            override fun onActivityResumed(activity: Activity) {
                weakActivity = WeakReference(activity)
                subject.onNext(ActivityEvent(activity,Lifecycle.Event.ON_RESUME))
            }

            override fun onActivityPaused(activity: Activity) {
                subject.onNext(ActivityEvent(activity,Lifecycle.Event.ON_PAUSE))
                weakActivity?.clear()
                weakActivity = null
            }

            override fun onActivityStopped(activity: Activity) {
                subject.onNext(ActivityEvent(activity,Lifecycle.Event.ON_STOP))
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                subject.onNext(ActivityEvent(activity,Lifecycle.Event.ON_DESTROY))
            }
        })
    }

}