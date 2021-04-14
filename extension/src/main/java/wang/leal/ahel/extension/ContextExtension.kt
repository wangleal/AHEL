package wang.leal.ahel.extension

import android.app.ActivityManager
import android.content.Context
import android.os.Process
import android.text.TextUtils
import android.util.TypedValue

fun Context.dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
}

fun Context.sp2px(sp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
}

fun Context.px2dip(px: Int): Float {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f)
}

fun Context.processName(): String? {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningAppProcesses = activityManager.runningAppProcesses ?: return null
    for (runningAppProcess in runningAppProcesses) {
        if (runningAppProcess.pid == Process.myPid()
                && !TextUtils.isEmpty(runningAppProcess.processName)) {
            return runningAppProcess.processName
        }
    }
    return null
}

fun Context.screenWidth(): Int {
    val dm = resources.displayMetrics
    return dm.widthPixels
}

fun Context.screenHeight(context: Context): Int {
    val dm = resources.displayMetrics
    return dm.heightPixels
}