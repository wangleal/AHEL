package wang.leal.ahel.image.scheduler

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

internal object GlideScheduler {

    private val glideScheduler = Schedulers.from(Executors.newSingleThreadExecutor { r -> Thread(r, "glide-scheduler") })
    fun scheduler():Scheduler{
        return glideScheduler
    }

}