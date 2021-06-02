package wang.leal.ahel.image

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object Image {
    @JvmStatic
    fun with(context:Context, model:Any): LoaderService {
        return GlideService(context,model)
    }

    @JvmStatic
    fun with(view:View, model:Any): LoaderService {
        return GlideService(view,model)
    }

    @JvmStatic
    fun with(activity: Activity, model:Any): LoaderService {
        return GlideService(activity,model)
    }

    @JvmStatic
    fun with(activity: FragmentActivity, model:Any): LoaderService {
        return GlideService(activity,model)
    }

    @JvmStatic
    fun with(fragment: Fragment, model:Any): LoaderService {
        return GlideService(fragment,model)
    }

    @JvmStatic
    fun with(fragment: android.app.Fragment, model:Any): LoaderService {
        return GlideService(fragment,model)
    }
}