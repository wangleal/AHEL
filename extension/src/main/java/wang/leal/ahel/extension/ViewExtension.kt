package wang.leal.ahel.extension

import android.view.View

fun View.isTouch(x: Float, y: Float): Boolean {
    val location = IntArray(2)
    getLocationOnScreen(location)
    val left = location[0]
    val top = location[1]
    val right = left + measuredWidth
    val bottom = top + measuredHeight
    return y >= top && y <= bottom && x >= left && x <= right
}

fun View.isTouchY(y: Float): Boolean {
    val location = IntArray(2)
    getLocationOnScreen(location)
    val top = location[1]
    val bottom = top + measuredHeight
    return y >= top && y <= bottom
}

fun View.isTouchX(x: Float): Boolean {
    val location = IntArray(2)
    getLocationOnScreen(location)
    val left = location[0]
    val right = left + measuredWidth
    return x >= left && x <= right
}