package wang.leal.ahel.player.video

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.TextureView
import android.widget.FrameLayout
import wang.leal.ahel.player.DataSource

class VideoView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    companion object{
        const val SCALE_MODE_FIT_CENTER = 1
        const val SCALE_MODE_CENTER_CROP = 2
    }
    private var player: VideoPlayer? = null
    private var textureView:TextureView? = null
    private var scaleMode = SCALE_MODE_CENTER_CROP

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        player = VideoPlayer.Builder(context).build()
        removeAllViews()
        textureView = TextureView(context)
        textureView?.let {
            val textureParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
            addView(it,0,textureParams)
            player?.display(it)
        }
        player?.setRenderedStartListener {
        }?.setCompletedListener {
        }?.setVideoSizeListener { videoWidth, videoHeight ->
            scaleTextureView(videoWidth,videoHeight)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeAllViews()
        player?.release()
    }

    fun scaleMode(scaleMode:Int): VideoView {
        this.scaleMode = scaleMode
        return this
    }

    fun loop(): VideoView {
        player?.loop()
        return this
    }

    fun mute(): VideoView {
        player?.mute()
        return this
    }

    /**
     * Continue play or replay
     */
    fun play(){
        player?.play()
    }

    fun dataSource(dataSource: DataSource): VideoView {
        player?.dataSource(dataSource)
        return this
    }

    private fun scaleTextureView(videoWidth:Int,videoHeight:Int){
        val centerX = width/2F
        val centerY = height/2F
        val matrix = Matrix()
        val tempHeight = width*videoHeight*1F/videoWidth
        matrix.postScale(1F,tempHeight*1F/height,centerX,centerY)
        if (scaleMode== SCALE_MODE_CENTER_CROP){
            val scale = 1F.coerceAtLeast(height * 1F / tempHeight)
            matrix.postScale(scale,scale,width*1F/2,height*1F/2)
        }else{
            val scale = 1F.coerceAtMost(height * 1F / tempHeight)
            matrix.postScale(scale,scale,width*1F/2,height*1F/2)
        }
        textureView?.setTransform(matrix)
    }
}