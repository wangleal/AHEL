package wang.leal.ahel.player

import wang.leal.ahel.player.video.VideoPreload

abstract class MediaPlayer {

    abstract fun play()
    abstract fun pause()
    abstract fun stop()
    fun preload(url:String){
        VideoPreload.preload(url)
    }
    open fun release(){
        VideoPreload.release()
    }
    abstract fun setVolume(volume:Float)
    abstract fun getVolume():Float
}