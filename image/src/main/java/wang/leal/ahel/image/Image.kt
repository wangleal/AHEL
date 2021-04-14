package wang.leal.ahel.image

import android.content.Context

object Image {
    fun with(context:Context, url:Any): LoaderService {
        return GlideService(context,url)
    }
}