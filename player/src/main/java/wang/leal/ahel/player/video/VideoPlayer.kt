package wang.leal.ahel.player.video

import android.content.Context
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import com.google.android.exoplayer2.*
import wang.leal.ahel.player.DataSource
import wang.leal.ahel.player.MediaPlayer

class VideoPlayer private constructor(private val player: SimpleExoPlayer)
    :Player.Listener, MediaPlayer(){

    private var completedListener:(()->Unit)? = null
    private var renderedStartListener:(()->Unit)? = null
    private var videoSizeListener:((width:Int,height:Int)->Unit)? = null
    private var errorListener:((error:Exception)->Unit)? = null

    init {
        player.addListener(this)
    }

    override fun onPlaybackStateChanged(state: Int) {
        if (state==ExoPlayer.STATE_ENDED){
            completedListener?.invoke()
        }
    }

    override fun onRenderedFirstFrame() {
        renderedStartListener?.invoke()
    }

    override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int, pixelWidthHeightRatio: Float) {
        videoSizeListener?.invoke(width,height)
    }

    override fun onPlayerError(error: PlaybackException) {
        errorListener?.invoke(error)
    }

    fun setCompletedListener(completedListener: () -> Unit): VideoPlayer {
        this.completedListener = completedListener
        return this
    }

    fun setRenderedStartListener(renderedStartListener: () -> Unit): VideoPlayer {
        this.renderedStartListener = renderedStartListener
        return this
    }

    fun setVideoSizeListener(videoSizeListener: (width:Int,height:Int) -> Unit): VideoPlayer {
        this.videoSizeListener = videoSizeListener
        return this
    }

    fun setErrorListener(errorListener:((error:Exception)->Unit)): VideoPlayer {
        this.errorListener = errorListener
        return this
    }

    fun loop(isLoop:Boolean = true): VideoPlayer {
        if (isLoop){
            player.repeatMode = Player.REPEAT_MODE_ONE
        }else{
            player.repeatMode = Player.REPEAT_MODE_ONE
        }
        return this
    }

    fun mute(): VideoPlayer {
        player.volume = 0f
        return this
    }

    fun dataSource(dataSource: DataSource): VideoPlayer {
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
        VideoCache.release()
    }

    fun display(textureView: TextureView): VideoPlayer {
        player.setVideoTextureView(textureView)
        return this
    }

    fun display(surfaceView: SurfaceView): VideoPlayer {
        player.setVideoSurfaceView(surfaceView)
        return this
    }

    fun display(surface: Surface): VideoPlayer {
        player.setVideoSurface(surface)
        return this
    }

    fun display(surfaceHolder: SurfaceHolder): VideoPlayer {
        player.setVideoSurfaceHolder(surfaceHolder)
        return this
    }

    override fun setVolume(volume:Float){
        player.volume = volume
    }

    override fun getVolume():Float{
        return player.volume
    }

    class Builder(val context: Context){

        fun build(): VideoPlayer {
            val builder = SimpleExoPlayer.Builder(context)
            return VideoPlayer(builder.build())
        }
    }
}