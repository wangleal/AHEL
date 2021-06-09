package wang.leal.ahel.bus

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

internal object BusScheduler {

    private val busScheduler = Schedulers.from(Executors.newSingleThreadExecutor { r -> Thread(r, "bus-scheduler") })
    fun scheduler():Scheduler{
        return busScheduler
    }

}