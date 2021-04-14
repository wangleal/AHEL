package wang.leal.ahel.bus

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

object BusScheduler {

    fun scheduler():Scheduler{
        return Schedulers.from(Executors.newSingleThreadExecutor { r -> Thread(r, "bus-scheduler") })
    }

}