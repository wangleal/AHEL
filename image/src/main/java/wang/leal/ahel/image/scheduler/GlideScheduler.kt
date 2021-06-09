package wang.leal.ahel.image.scheduler

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

internal object GlideScheduler {

    fun scheduler():Scheduler{
        return Schedulers.io()
    }

}