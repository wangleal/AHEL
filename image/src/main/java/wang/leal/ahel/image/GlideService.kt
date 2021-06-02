package wang.leal.ahel.image

import android.content.Context
import android.graphics.Bitmap
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import wang.leal.ahel.image.transformation.RoundedCornersTransformation


class GlideService(val context: Context?, private val url: Any):LoaderService {
    private var placeholder:Int = -1
    private var error:Int = -1

    override fun placeholder(placeholder: Int):GlideService{
        this.placeholder = placeholder
        return this
    }

    override fun error(error: Int):GlideService{
        this.error = error
        return this
    }

    override fun display(imageView: ImageView){
        if (context==null) {
            return
        }
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .into(imageView)
    }

    override fun displayCircle(imageView: ImageView){
        if (context==null) {
            return
        }
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .centerCrop()
                .apply(RequestOptions().circleCrop())
                .into(imageView)
    }

    override fun displayRound(radiusDp: Float, imageView: ImageView){
        if (context==null) {
            return
        }
        val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(dp2px(radiusDp)))
        val options = RequestOptions.bitmapTransform(multiTransform).override(imageView.width, imageView.height)
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .centerCrop()
                .apply(options)
                .into(imageView)
    }

    override fun displayTopRound(radiusDp: Float, imageView: ImageView) {
        if (context==null) {
            return
        }
        val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(dp2px(radiusDp), RoundedCornersTransformation.CornerType.TOP))
        val options = RequestOptions.bitmapTransform(multiTransform).override(imageView.width, imageView.height)
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .centerCrop()
                .apply(options)
                .into(imageView)
    }

    override fun displayBottomRound(radiusDp: Float, imageView: ImageView) {
        if (context==null) {
            return
        }
        val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(dp2px(radiusDp), RoundedCornersTransformation.CornerType.BOTTOM))
        val options = RequestOptions.bitmapTransform(multiTransform).override(imageView.width, imageView.height)
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .centerCrop()
                .apply(options)
                .into(imageView)
    }

    override fun load(): Observable<Bitmap> {
        return Observable.create<Bitmap>{
            if (context==null) {
                it.onError(NullPointerException("Context can not be null!"))
            }else{
                val futureTarget =Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .placeholder(placeholder)
                        .error(error)
                        .submit()
                it.setDisposable(GlideTargetDisposable(futureTarget))
                try {
                    val bitmap = futureTarget.get()
                    it.onNext(bitmap)
                    it.onComplete()
                }catch (e:Exception){
                    it.onError(e)
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadCircle(): Observable<Bitmap> {
        return Observable.create<Bitmap>{
            if (context==null) {
                it.onError(NullPointerException("Context can not be null!"))
            }else{
                val futureTarget =Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .placeholder(placeholder)
                        .error(error)
                        .centerCrop()
                        .apply(RequestOptions().circleCrop())
                        .submit()
                it.setDisposable(GlideTargetDisposable(futureTarget))
                try {
                    val bitmap = futureTarget.get()
                    it.onNext(bitmap)
                    it.onComplete()
                }catch (e:Exception){
                    it.onError(e)
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadRound(radiusDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap>{
            if (context==null) {
                it.onError(NullPointerException("Context can not be null!"))
            }else{
                val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                        CenterCrop(),
                        RoundedCornersTransformation(dp2px(radiusDp)))
                val options = RequestOptions.bitmapTransform(multiTransform)
                val futureTarget =Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .placeholder(placeholder)
                        .error(error)
                        .centerCrop()
                        .apply(options)
                        .submit()
                it.setDisposable(GlideTargetDisposable(futureTarget))
                try {
                    val bitmap = futureTarget.get()
                    it.onNext(bitmap)
                    it.onComplete()
                }catch (e:Exception){
                    it.onError(e)
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadRound(radiusDp: Float, widthDp: Float, heightDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap>{
            if (context==null) {
                it.onError(NullPointerException("Context can not be null!"))
            }else{
                val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                        CenterCrop(),
                        RoundedCornersTransformation(dp2px(radiusDp)))
                val options = RequestOptions.bitmapTransform(multiTransform).override(dp2px(widthDp), dp2px(heightDp))
                val futureTarget =Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .placeholder(placeholder)
                        .error(error)
                        .centerCrop()
                        .apply(options)
                        .submit()
                it.setDisposable(GlideTargetDisposable(futureTarget))
                try {
                    val bitmap = futureTarget.get()
                    it.onNext(bitmap)
                    it.onComplete()
                }catch (e:Exception){
                    it.onError(e)
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadTopRound(radiusDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap>{
            if (context==null) {
                it.onError(NullPointerException("Context can not be null!"))
            }else{
                val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                        CenterCrop(),
                        RoundedCornersTransformation(dp2px(radiusDp), RoundedCornersTransformation.CornerType.TOP))
                val options = RequestOptions.bitmapTransform(multiTransform)
                val futureTarget =Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .placeholder(placeholder)
                        .error(error)
                        .centerCrop()
                        .apply(options)
                        .submit()
                it.setDisposable(GlideTargetDisposable(futureTarget))
                try {
                    val bitmap = futureTarget.get()
                    it.onNext(bitmap)
                    it.onComplete()
                }catch (e:Exception){
                    it.onError(e)
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadTopRound(radiusDp: Float, widthDp: Float, heightDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap>{
            if (context==null) {
                it.onError(NullPointerException("Context can not be null!"))
            }else{
                val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                        CenterCrop(),
                        RoundedCornersTransformation(dp2px(radiusDp), RoundedCornersTransformation.CornerType.TOP))
                val options = RequestOptions.bitmapTransform(multiTransform).override(dp2px(widthDp), dp2px(heightDp))
                val futureTarget =Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .placeholder(placeholder)
                        .error(error)
                        .centerCrop()
                        .apply(options)
                        .submit()
                it.setDisposable(GlideTargetDisposable(futureTarget))
                try {
                    val bitmap = futureTarget.get()
                    it.onNext(bitmap)
                    it.onComplete()
                }catch (e:Exception){
                    it.onError(e)
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadBottomRound(radiusDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap>{
            if (context==null) {
                it.onError(NullPointerException("Context can not be null!"))
            }else{
                val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                        CenterCrop(),
                        RoundedCornersTransformation(dp2px(radiusDp), RoundedCornersTransformation.CornerType.BOTTOM))
                val options = RequestOptions.bitmapTransform(multiTransform)
                val futureTarget =Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .placeholder(placeholder)
                        .error(error)
                        .centerCrop()
                        .apply(options)
                        .submit()
                it.setDisposable(GlideTargetDisposable(futureTarget))
                try {
                    val bitmap = futureTarget.get()
                    it.onNext(bitmap)
                    it.onComplete()
                }catch (e:Exception){
                    it.onError(e)
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadBottomRound(radiusDp: Float, widthDp: Float, heightDp: Float): Observable<Bitmap> {
        return Observable.create<Bitmap>{
            if (context==null) {
                it.onError(NullPointerException("Context can not be null!"))
            }else{
                val multiTransform: MultiTransformation<Bitmap> = MultiTransformation(
                        CenterCrop(),
                        RoundedCornersTransformation(dp2px(radiusDp), RoundedCornersTransformation.CornerType.BOTTOM))
                val options = RequestOptions.bitmapTransform(multiTransform).override(dp2px(widthDp), dp2px(heightDp))
                val futureTarget =Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .placeholder(placeholder)
                        .error(error)
                        .centerCrop()
                        .apply(options)
                        .submit()
                it.setDisposable(GlideTargetDisposable(futureTarget))
                try {
                    val bitmap = futureTarget.get()
                    it.onNext(bitmap)
                    it.onComplete()
                }catch (e:Exception){
                    it.onError(e)
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    inner class GlideTargetDisposable(private val target: Target<Bitmap>):Disposable{
        @Volatile private var disposed = false
        override fun dispose() {
            disposed = true
            context?.let {
                Glide.with(context).clear(target)
            }
        }

        override fun isDisposed(): Boolean {
            return disposed
        }

    }

    private fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context?.resources?.displayMetrics).toInt()
    }
}