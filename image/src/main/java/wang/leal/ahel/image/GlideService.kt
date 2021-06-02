package wang.leal.ahel.image

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import wang.leal.ahel.image.transformation.RoundedCornersTransformation

class GlideService(private val showTarget: Any, private val model: Any) : LoaderService {
    private var placeholder: Int = -1
    private var error: Int = -1

    override fun placeholder(placeholder: Int): GlideService {
        this.placeholder = placeholder
        return this
    }

    override fun error(error: Int): GlideService {
        this.error = error
        return this
    }

    override fun display(imageView: ImageView) {
        requestManager()
            ?.load(model)
            ?.placeholder(placeholder)
            ?.error(error)
            ?.into(imageView)
    }

    override fun displayCircle(imageView: ImageView) {
        requestManager()
            ?.load(model)
            ?.placeholder(placeholder)
            ?.error(error)
            ?.centerCrop()
            ?.apply(RequestOptions().circleCrop())
            ?.into(imageView)
    }

    override fun displayRound(radiusDp: Float, imageView: ImageView) {
        val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
            CenterCrop(),
            RoundedCornersTransformation(dp2px(radiusDp))
        )
        val options = RequestOptions.bitmapTransform(multiTransform)
            .override(imageView.width, imageView.height)
        requestManager()
            ?.load(model)
            ?.placeholder(placeholder)
            ?.error(error)
            ?.centerCrop()
            ?.apply(options)
            ?.into(imageView)
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
        requestManager()
            ?.load(model)
            ?.placeholder(placeholder)
            ?.error(error)
            ?.centerCrop()
            ?.apply(options)
            ?.into(imageView)
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
        requestManager()
            ?.load(model)
            ?.placeholder(placeholder)
            ?.error(error)
            ?.centerCrop()
            ?.apply(options)
            ?.into(imageView)
    }

    override fun load(): Observable<Bitmap> {
        return Observable.create<Bitmap> {emitter->
            val futureTarget = requestManager()
                ?.asBitmap()
                ?.load(model)
                ?.placeholder(placeholder)
                ?.error(error)
                ?.submit()
            futureTarget?.let {
                emitter.setDisposable(GlideTargetDisposable(futureTarget))
            }
            try {
                val bitmap = futureTarget?.get()
                emitter.onNext(bitmap)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadCircle(): Observable<Bitmap> {
        return Observable.create<Bitmap> {emitter->
            val futureTarget = requestManager()
                ?.asBitmap()
                ?.load(model)
                ?.placeholder(placeholder)
                ?.error(error)
                ?.centerCrop()
                ?.apply(RequestOptions().circleCrop())
                ?.submit()
            futureTarget?.let {
                emitter.setDisposable(GlideTargetDisposable(futureTarget))
            }
            try {
                val bitmap = futureTarget?.get()
                emitter.onNext(bitmap)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadRound(radiusDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap> {emitter->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(dp2px(radiusDp))
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
            val futureTarget = requestManager()
                ?.asBitmap()
                ?.load(model)
                ?.placeholder(placeholder)
                ?.error(error)
                ?.centerCrop()
                ?.apply(options)
                ?.submit()
            futureTarget?.let {
                emitter.setDisposable(GlideTargetDisposable(futureTarget))
            }
            try {
                val bitmap = futureTarget?.get()
                emitter.onNext(bitmap)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadRound(radiusDp: Float, widthDp: Float, heightDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap> {emitter->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(dp2px(radiusDp))
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
                .override(dp2px(widthDp), dp2px(heightDp))
            val futureTarget = requestManager()
                ?.asBitmap()
                ?.load(model)
                ?.placeholder(placeholder)
                ?.error(error)
                ?.centerCrop()
                ?.apply(options)
                ?.submit()
            futureTarget?.let {
                emitter.setDisposable(GlideTargetDisposable(futureTarget))
            }
            try {
                val bitmap = futureTarget?.get()
                emitter.onNext(bitmap)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadTopRound(radiusDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap> {emitter->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dp2px(radiusDp),
                    RoundedCornersTransformation.CornerType.TOP
                )
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
            val futureTarget = requestManager()
                ?.asBitmap()
                ?.load(model)
                ?.placeholder(placeholder)
                ?.error(error)
                ?.centerCrop()
                ?.apply(options)
                ?.submit()
            futureTarget?.let {
                emitter.setDisposable(GlideTargetDisposable(futureTarget))
            }
            try {
                val bitmap = futureTarget?.get()
                emitter.onNext(bitmap)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadTopRound(
        radiusDp: Float,
        widthDp: Float,
        heightDp: Float
    ): Observable<Bitmap> {
        return Observable.create<Bitmap> {emitter->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dp2px(radiusDp),
                    RoundedCornersTransformation.CornerType.TOP
                )
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
                .override(dp2px(widthDp), dp2px(heightDp))
            val futureTarget = requestManager()
                ?.asBitmap()
                ?.load(model)
                ?.placeholder(placeholder)
                ?.error(error)
                ?.centerCrop()
                ?.apply(options)
                ?.submit()
            futureTarget?.let {
                emitter.setDisposable(GlideTargetDisposable(futureTarget))
            }
            try {
                val bitmap = futureTarget?.get()
                emitter.onNext(bitmap)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadBottomRound(radiusDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap> {emitter->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dp2px(radiusDp),
                    RoundedCornersTransformation.CornerType.BOTTOM
                )
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
            val futureTarget = requestManager()
                ?.asBitmap()
                ?.load(model)
                ?.placeholder(placeholder)
                ?.error(error)
                ?.centerCrop()
                ?.apply(options)
                ?.submit()
            futureTarget?.let {
                emitter.setDisposable(GlideTargetDisposable(futureTarget))
            }
            try {
                val bitmap = futureTarget?.get()
                emitter.onNext(bitmap)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadBottomRound(
        radiusDp: Float,
        widthDp: Float,
        heightDp: Float
    ): Observable<Bitmap> {
        return Observable.create<Bitmap> {emitter->
            val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dp2px(radiusDp),
                    RoundedCornersTransformation.CornerType.BOTTOM
                )
            )
            val options = RequestOptions.bitmapTransform(multiTransform)
                .override(dp2px(widthDp), dp2px(heightDp))
            val futureTarget = requestManager()
                ?.asBitmap()
                ?.load(model)
                ?.placeholder(placeholder)
                ?.error(error)
                ?.centerCrop()
                ?.apply(options)
                ?.submit()
            futureTarget?.let {
                emitter.setDisposable(GlideTargetDisposable(futureTarget))
            }
            try {
                val bitmap = futureTarget?.get()
                emitter.onNext(bitmap)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    private fun getContext():Context?{
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
}