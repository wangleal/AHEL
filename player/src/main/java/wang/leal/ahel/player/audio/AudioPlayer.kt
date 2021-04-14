package wang.leal.ahel.player.audio

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.device.DeviceListener
import com.google.android.exoplayer2.video.VideoListener
import wang.leal.ahel.player.DataSource
import wang.leal.ahel.player.MediaPlayer
import wang.leal.ahel.player.video.VideoCache

class AudioPlayer private constructor(private val player: SimpleExoPlayer)
    : DeviceListener, Player.EventListener, VideoListener , MediaPlayer(){
    private var completedListener:(()->Unit)? = null
    private var renderedStartListener:(()->Unit)? = null
    private var videoSizeListener:((width:Int,height:Int)->Unit)? = null

    init {
        player.addDeviceListener(this)
        player.addListener(this)
        player.addVideoListener(this)
    }

    override fun onPlaybackStateChanged(state: Int) {
        if (state== ExoPlayer.STATE_ENDED){
            completedListener?.invoke()
        }
    }

    override fun onRenderedFirstFrame() {
        renderedStartListener?.invoke()
    }

    override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int, pixelWidthHeightRatio: Float) {
        videoSizeListener?.invoke(width,height)
    }

    fun setCompletedListener(completedListener: () -> Unit): AudioPlayer {
        this.completedListener = completedListener
        return this
    }

    fun setRenderedStartListener(renderedStartListener: () -> Unit): AudioPlayer {
        this.renderedStartListener = renderedStartListener
        return this
    }

    fun setVideoSizeListener(videoSizeListener: (width:Int,height:Int) -> Unit): AudioPlayer {
        this.videoSizeListener = videoSizeListener
        return this
    }

    fun loop(): AudioPlayer {
        return loop(true)
    }

    fun loop(isLoop:Boolean): AudioPlayer {
        if (isLoop){
            player.repeatMode = Player.REPEAT_MODE_ONE
        }else{
            player.repeatMode = Player.REPEAT_MODE_ONE
        }
        return this
    }

    fun mute(): AudioPlayer {
        player.volume = 0f
        return this
    }

    fun dataSource(dataSource: DataSource): AudioPlayer {
        player.setMediaItem(MediaItem.fromUri(VideoCache.cache(dataSource.getUri())))
        return this
    }

    override fun play(){
        if (player.playbackState == Player.STATE_ENDED){
            player.seekTo(0)
        }else if (player.playbackState == Player.STATE_IDLE){
            player.prepare()
        }
        player.play()
    }

    override fun pause(){
        player.pause()
    }

    override fun stop(){
        player.stop()
    }

    override fun release(){
        super.release()
        player.release()
    }

    override fun setVolume(volume:Float){
        player.volume = volume
    }

    override fun getVolume():Float{
        return player.volume
    }

    class Builder(val context: Context){

        fun build(): AudioPlayer {
            val builder = SimpleExoPlayer.Builder(context)
            return AudioPlayer(builder.build())
        }
    }
}