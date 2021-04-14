package wang.leal.ahel.storage.room

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

object RoomScheduler {

    fun scheduler():Scheduler{
        return Schedulers.from(Executors.newSingleThreadExecutor { r -> Thread(r, "storage-scheduler") })
    }

}