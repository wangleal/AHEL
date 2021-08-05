package wang.leal.ahel.image

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.disposables.Disposable
import wang.leal.ahel.image.scheduler.GlideScheduler
import wang.leal.ahel.image.transformation.RoundedCornersTransformation

class GlideService(private val showTarget: Any, private val model: Any) : LoaderService {
    private var placeholder: Int = -1
    private var error: Int = -1
    private var requestListener:RequestListener? = null

    override fun placeholder(placeholder: Int): GlideService {
        this.placeholder = placeholder
        return this
    }

    override fun error(error: Int): GlideService {
        this.error = error
        return this
    }

    override fun listener(requestListener: RequestListener): GlideService {
        this.requestListener = requestListener
        return this
    }

    override fun display(imageView: ImageView) {
        displayImage(RequestOptions(),imageView)
    }

    override fun displayCircle(imageView: ImageView) {
        displayImage(RequestOptions().circleCrop(),imageView)
    }

    override fun displayRound(radiusDp: Float, imageView: ImageView) {
        val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
            CenterCrop(),
            RoundedCornersTransformation(dp2px(radiusDp))
        )
        val options = RequestOptions.bitmapTransform(multiTransform)
            .override(imageView.width, imageView.height)
        displayImage(options,imageView)
    }

    override fun displayTopRound(radiusDp: Float, imageView: ImageView) {
        val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
            CenterCrop(),
            RoundedCornersTransformation(
                dp2px(radiusDp),
                RoundedCornersTransformation.CornerType.TOP
            )
        )
        val options = RequestOptions.bitmapTransform(multiTransform)
            .override(imageView.width, imageView.height)
        displayImage(options,imageView)
    }

    override fun displayBottomRound(radiusDp: Float, imageView: ImageView) {
        val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
            CenterCrop(),
            RoundedCornersTransformation(
                dp2px(radiusDp),
                RoundedCornersTransformation.CornerType.BOTTOM
            )
        )
        val options = RequestOptions.bitmapTransform(multiTransform)
            .override(imageView.width, imageView.height)
        displayImage(options,imageView)
    }

    private fun displayImage(options:RequestOptions,imageView: ImageView){
        placeholder = if (placeholder==-1){error}else placeholder
        error = if (error==-1){placeholder}else error
        requestManager()
            ?.load(model)
            ?.thumbnail(transformPlaceholderDrawableBuilder(options))
            ?.error(transformErrorDrawableBuilder(options))
            ?.centerCrop()
            ?.apply(options)
            ?.listener(object :com.bumptech.glide.request.RequestListener<Drawable>{
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
        return Observable.create<Bitmap> { emitter ->
            loadBitmap(RequestOptions(), emitter)
        }.subscribeOn(GlideScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadCircle(): Observable<Bitmap> {
        return Observable.create<Bitmap> { emitter ->
            loadBitmap(RequestOptions().circleCrop(), emitter)
        }.subscribeOn(GlideScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadRound(radiusDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap> { emitter ->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(dp2px(radiusDp))
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
            loadBitmap(options, emitter)
        }.subscribeOn(GlideScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadRound(radiusDp: Float, widthDp: Float, heightDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap> { emitter ->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(dp2px(radiusDp))
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
                .override(dp2px(widthDp), dp2px(heightDp))
            loadBitmap(options, emitter)
        }.subscribeOn(GlideScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadTopRound(radiusDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap> { emitter ->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dp2px(radiusDp),
                    RoundedCornersTransformation.CornerType.TOP
                )
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
            loadBitmap(options, emitter)
        }.subscribeOn(GlideScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadTopRound(
        radiusDp: Float,
        widthDp: Float,
        heightDp: Float
    ): Observable<Bitmap> {
        return Observable.create<Bitmap> { emitter ->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dp2px(radiusDp),
                    RoundedCornersTransformation.CornerType.TOP
                )
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
                .override(dp2px(widthDp), dp2px(heightDp))
            loadBitmap(options, emitter)
        }.subscribeOn(GlideScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadBottomRound(radiusDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap> { emitter ->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dp2px(radiusDp),
                    RoundedCornersTransformation.CornerType.BOTTOM
                )
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
            loadBitmap(options, emitter)
        }.subscribeOn(GlideScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadBottomRound(
        radiusDp: Float,
        widthDp: Float,
        heightDp: Float
    ): Observable<Bitmap> {
        return Observable.create<Bitmap> { emitter ->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dp2px(radiusDp),
                    RoundedCornersTransformation.CornerType.BOTTOM
                )
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
                .override(dp2px(widthDp), dp2px(heightDp))
            loadBitmap(options, emitter)
        }.subscribeOn(GlideScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun loadBitmap(
        options: RequestOptions,
        emitter: @NonNull ObservableEmitter<Bitmap>
    ) {
        placeholder = if (placeholder==-1){error}else placeholder
        error = if (error==-1){placeholder}else error
        val futureTarget = requestManager()
            ?.asBitmap()
            ?.load(model)
            ?.thumbnail(transformPlaceholderBitmapBuilder(options))
            ?.error(transformErrorBitmapBuilder(options))
            ?.centerCrop()
            ?.apply(options.skipMemoryCache(true))
            ?.listener(object :com.bumptech.glide.request.RequestListener<Bitmap>{
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
                emitter.onError(RuntimeException("Bitmap is recycled!"))
            }
        } catch (e: Exception) {
            emitter.onError(e)
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

    private fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp, getContext()?.resources?.displayMetrics
        ).toInt()
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

    private fun transformPlaceholderDrawableBuilder(options:RequestOptions):RequestBuilder<Drawable>?{
        return requestManager()?.load(placeholder)?.apply(options)
    }

    private fun transformPlaceholderBitmapBuilder(options:RequestOptions):RequestBuilder<Bitmap>?{
        return requestManager()?.asBitmap()?.load(placeholder)?.apply(options)
    }

    private fun transformErrorDrawableBuilder(options:RequestOptions):RequestBuilder<Drawable>?{
        return requestManager()?.load(error)?.apply(options)
    }

    private fun transformErrorBitmapBuilder(options:RequestOptions):RequestBuilder<Bitmap>?{
        return requestManager()?.asBitmap()?.load(error)?.apply(options)
    }
}