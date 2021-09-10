package wang.leal.ahel.image

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.disposables.Disposable
import wang.leal.ahel.image.scheduler.GlideScheduler

class GlideService(private val showTarget: Any, private val model: Any) : LoaderService {
    private var placeholder: Int = -1
    private var error: Int = -1
    private var requestListener: RequestListener? = null
    private var scaleType: Int = -1//1:centerCrop,2:centerInside,3:fitCenter
    private var shapeType: Int = -1//1:circle,2:round
    private var roundedCorners:RectF = RectF()

    override fun placeholder(placeholder: Int): GlideService {
        this.placeholder = placeholder
        return this
    }

    override fun error(error: Int): GlideService {
        this.error = error
        return this
    }

    override fun centerCrop(): LoaderService {
        this.scaleType = 1
        return this
    }

    override fun centerInside(): LoaderService {
        this.scaleType = 2
        return this
    }

    override fun fitCenter(): LoaderService {
        this.scaleType = 3
        return this
    }

    override fun circle(): LoaderService {
        this.shapeType = 1
        return this
    }

    override fun round(radiusDp: Float): LoaderService {
        return round(radiusDp,radiusDp,radiusDp,radiusDp)
    }

    override fun round(
        leftTop: Float,
        rightTop: Float,
        rightBottom: Float,
        rightLeft: Float
    ): LoaderService {
        this.shapeType = 2
        this.roundedCorners.left = leftTop
        this.roundedCorners.top = rightTop
        this.roundedCorners.right = rightBottom
        this.roundedCorners.bottom = rightLeft
        return this
    }

    override fun listener(requestListener: RequestListener): GlideService {
        this.requestListener = requestListener
        return this
    }

    override fun display(imageView: ImageView) {
        var options = dealOptions()
        if (shapeType==2){
            options = options.override(imageView.width,imageView.height)
        }
        displayImage(options, imageView)
    }

    private fun displayImage(options: RequestOptions, imageView: ImageView) {
        requestManager()
            ?.load(model)
            ?.thumbnail(transformPlaceholderDrawableBuilder(options))
            ?.error(transformErrorDrawableBuilder(options))
            ?.apply(options)
            ?.listener(object : com.bumptech.glide.request.RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    requestListener?.onSuccess()
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    requestListener?.onFailure()
                    return false
                }
            })
            ?.into(imageView)
    }

    override fun load(): Observable<Bitmap> {
        return load(0f,0f)
    }

    override fun load(widthDp: Float, heightDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap> { emitter ->
            var options = dealOptions()
            if (widthDp>0&&heightDp>0){
                options = options.override(dp2px(widthDp).toInt(), dp2px(heightDp).toInt())
            }
            loadBitmap(options, emitter)
        }.subscribeOn(GlideScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun loadBitmap(
        options: RequestOptions,
        emitter: @NonNull ObservableEmitter<Bitmap>
    ) {
        var errorBitmap:Bitmap? = null
        if (error>0){
            val errorTarget = transformErrorBitmapBuilder(options)
                ?.submit()
            try {
                val bitmap = errorTarget?.get()
                if (bitmap?.isRecycled == false) {
                    errorBitmap = Bitmap.createBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (placeholder>0){
            val placeHolderTarget = transformPlaceholderBitmapBuilder(options)
                ?.submit()
            try {
                val bitmap = placeHolderTarget?.get()
                if (bitmap?.isRecycled == false) {
                    val placeHolderBitmap = Bitmap.createBitmap(bitmap)
                    emitter.onNext(placeHolderBitmap)
                } else {
                    errorBitmap?.let {
                        emitter.onNext(errorBitmap)
                    }
                }
            } catch (e: Exception) {
                errorBitmap?.let {
                    emitter.onNext(errorBitmap)
                }
            }
        }

        val futureTarget = requestManager()
            ?.asBitmap()
            ?.load(model)
            ?.apply(options.skipMemoryCache(true))
            ?.listener(object : com.bumptech.glide.request.RequestListener<Bitmap> {
                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    requestListener?.onSuccess()
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    requestListener?.onFailure()
                    return false
                }
            })
            ?.submit()
        futureTarget?.let {
            emitter.setDisposable(GlideTargetDisposable(futureTarget))
        }
        try {
            val bitmap = futureTarget?.get()
            if (bitmap?.isRecycled == false) {
                val tempBitmap = Bitmap.createBitmap(bitmap)
                emitter.onNext(tempBitmap)
                emitter.onComplete()
            } else {
                errorBitmap?.let {
                    emitter.onNext(errorBitmap)
                    emitter.onComplete()
                }.let {
                    emitter.onError(RuntimeException("Bitmap is recycled! Also Error bitmap is null."))
                }
            }
        } catch (e: Exception) {
            errorBitmap?.let {
                emitter.onNext(errorBitmap)
                emitter.onComplete()
            }.let {
                emitter.onError(e)
            }
        }
    }

    inner class GlideTargetDisposable(private val target: Target<Bitmap>) : Disposable {
        @Volatile
        private var disposed = false
        override fun dispose() {
            disposed = true
            requestManager()?.clear(target)
        }

        override fun isDisposed(): Boolean {
            return disposed
        }

    }

    private fun dealOptions():RequestOptions{
        val scaleTransformation = when (scaleType) {
            1 ->
                CenterCrop()
            2 ->
                CenterInside()
            3 ->
                FitCenter()
            else -> null
        }

        val shapeTransformation = when (shapeType){
            1 ->
                CircleCrop()
            2 ->
                GranularRoundedCorners(dp2px(roundedCorners.left),
                    dp2px(roundedCorners.top),
                    dp2px(roundedCorners.right),
                    dp2px(roundedCorners.bottom))
            else -> null
        }

        val options = if (scaleTransformation!=null&&shapeTransformation!=null){
            RequestOptions.bitmapTransform(MultiTransformation(
                scaleTransformation,shapeTransformation
            ))
        }else if (scaleTransformation==null&&shapeTransformation==null){
            RequestOptions()
        }else{
            val transformation = scaleTransformation ?: shapeTransformation
            transformation?.let {
                RequestOptions.bitmapTransform(transformation)
            }?: RequestOptions()
        }

        placeholder = if (placeholder == -1) {
            error
        } else placeholder
        error = if (error == -1) {
            placeholder
        } else error
        return options
    }

    private fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp, getContext()?.resources?.displayMetrics
        )
    }

    private fun requestManager(): RequestManager? {
        return when (showTarget) {
            is FragmentActivity -> {
                Glide.with(showTarget)
            }
            is Activity -> {
                Glide.with(showTarget)
            }
            is View -> {
                Glide.with(showTarget)
            }
            is Fragment -> {
                Glide.with(showTarget)
            }
            is android.app.Fragment -> {
                Glide.with(showTarget)
            }
            is Context -> {
                Glide.with(showTarget)
            }
            else -> {
                null
            }
        }
    }

    private fun getContext(): Context? {
        return when (showTarget) {
            is FragmentActivity -> {
                showTarget
            }
            is Activity -> {
                showTarget
            }
            is View -> {
                showTarget.context
            }
            is Fragment -> {
                showTarget.context
            }
            is android.app.Fragment -> {
                showTarget.activity
            }
            is Context -> {
                showTarget
            }
            else -> {
                null
            }
        }
    }

    private fun transformPlaceholderDrawableBuilder(options: RequestOptions): RequestBuilder<Drawable>? {
        return requestManager()?.load(placeholder)?.apply(options)
    }

    private fun transformPlaceholderBitmapBuilder(options: RequestOptions): RequestBuilder<Bitmap>? {
        return requestManager()?.asBitmap()?.load(placeholder)?.apply(options)
    }

    private fun transformErrorDrawableBuilder(options: RequestOptions): RequestBuilder<Drawable>? {
        return requestManager()?.load(error)?.apply(options)
    }

    private fun transformErrorBitmapBuilder(options: RequestOptions): RequestBuilder<Bitmap>? {
        return requestManager()?.asBitmap()?.load(error)?.apply(options)
    }
}