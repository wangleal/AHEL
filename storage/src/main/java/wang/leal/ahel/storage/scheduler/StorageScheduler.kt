package wang.leal.ahel.storage.scheduler

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

internal object StorageScheduler {

    fun scheduler():Scheduler{
        return Schedulers.io()
    }

}