package wang.leal.ahel.image

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import io.reactivex.rxjava3.core.Observable

interface LoaderService {
    fun placeholder(@DrawableRes placeholder:Int): LoaderService
    fun error(@DrawableRes error:Int): LoaderService
    fun centerCrop():LoaderService
    fun centerInside():LoaderService
    fun fitCenter():LoaderService
    fun circle():LoaderService
    fun round(radiusDp: Float):LoaderService
    fun round(leftTop:Float=0f,rightTop:Float=0f,rightBottom:Float=0f,rightLeft:Float=0f):LoaderService
    fun listener(requestListener:RequestListener):LoaderService
    fun display(imageView:ImageView)
    fun load(): Observable<Bitmap>
    fun load(widthDp:Float,heightDp:Float): Observable<Bitmap>
}

interface RequestListener{
    fun onSuccess()
    fun onFailure()
}