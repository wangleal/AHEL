package wang.leal.ahel.player

import android.content.Context
import wang.leal.ahel.player.video.VideoCache

object Player {

    fun initialize(context:Context){
        VideoCache.initialize(context)
    }
}