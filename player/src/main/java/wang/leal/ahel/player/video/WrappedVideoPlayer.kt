package wang.leal.ahel.player.video

import android.content.Context
import android.graphics.Matrix
import android.view.TextureView
import android.view.ViewGroup
import wang.leal.ahel.player.DataSource

class WrappedVideoPlayer(context:Context) {
    companion object{
        const val SCALE_MODE_FIT_CENTER = 1
        const val SCALE_MODE_CENTER_CROP = 2
    }
    private val player: VideoPlayer = VideoPlayer.Builder(context).build()
    private var textureView: TextureView? = null
    private var scaleMode = SCALE_MODE_CENTER_CROP

    init {
        player.setVideoSizeListener { width, height ->
            scaleTextureView(width,height)
        }
    }

    fun scaleMode(scaleMode:Int): WrappedVideoPlayer {
        this.scaleMode = scaleMode
        return this
    }

    fun mute(): WrappedVideoPlayer {
        player.mute()
        return this
    }

    fun loop(): WrappedVideoPlayer {
        player.loop()
        return this
    }

    /**
     * Continue play or replay
     */
    fun play(){
        player.play()
    }

    fun pause(){
        player.pause()
    }

    fun stop(){
        player.stop()
    }

    fun dataSource(dataSource: DataSource): WrappedVideoPlayer {
        player.dataSource(dataSource)
        return this
    }

    fun display(container:ViewGroup): WrappedVideoPlayer {
        if (textureView?.parent != container) {
            removeTextureView()
            textureView = TextureView(container.context)
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            container.addView(textureView,params)
            textureView?.let {
                player.display(it)
            }
        }
        return this
    }

    private fun removeTextureView() {
        textureView?.let {
            (it.parent as ViewGroup?)?.removeView(it)
            textureView = null
        }
    }

    private fun scaleTextureView(videoWidth:Int,videoHeight:Int){
        val width = textureView?.width?:0
        val height = textureView?.height?:0
        val centerX = width/2F
        val centerY = height/2F
        val matrix = Matrix()
        val tempHeight = width*videoHeight*1F/videoWidth
        matrix.postScale(1F,tempHeight*1F/height,centerX,centerY)
        if (scaleMode== VideoView.SCALE_MODE_CENTER_CROP){
            val scale = 1F.coerceAtLeast(height * 1F / tempHeight)
            matrix.postScale(scale,scale,width*1F/2,height*1F/2)
        }else{
            val scale = 1F.coerceAtMost(height * 1F / tempHeight)
            matrix.postScale(scale,scale,width*1F/2,height*1F/2)
        }
        textureView?.setTransform(matrix)
    }

    fun release(){
        removeTextureView()
        player.release()
    }

}