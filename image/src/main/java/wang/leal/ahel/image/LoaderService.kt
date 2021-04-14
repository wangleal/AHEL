package wang.leal.ahel.image

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import io.reactivex.rxjava3.core.Observable

interface LoaderService {

    fun placeholder(@DrawableRes placeholder:Int):GlideService
    fun error(error:Int):GlideService
    fun display(imageView:ImageView)
    fun displayCircle(imageView:ImageView)
    fun displayRound(radiusDp:Float,imageView: ImageView)
    fun displayTopRound(radiusDp:Float,imageView: ImageView)
    fun displayBottomRound(radiusDp:Float,imageView: ImageView)
    fun load(): Observable<Bitmap>
    fun loadCircle(): Observable<Bitmap>
    fun loadRound(radiusDp:Float): Observable<Bitmap>
    fun loadTopRound(radiusDp:Float): Observable<Bitmap>
    fun loadBottomRound(radiusDp:Float): Observable<Bitmap>
    fun loadRound(radiusDp:Float,widthDp:Float,heightDp:Float): Observable<Bitmap>
    fun loadTopRound(radiusDp:Float,widthDp:Float,heightDp:Float): Observable<Bitmap>
    fun loadBottomRound(radiusDp:Float,widthDp:Float,heightDp:Float): Observable<Bitmap>
}