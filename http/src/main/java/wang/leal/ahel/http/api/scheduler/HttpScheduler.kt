package wang.leal.ahel.http.api.scheduler

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

internal object HttpScheduler {

    @JvmStatic
    fun scheduler():Scheduler{
        return Schedulers.io()
    }

}