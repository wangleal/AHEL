package wang.leal.ahel.extension

import android.graphics.Color

inline fun <A, B, R> notNull(first: A?, second: B?, block: (A, B) -> R): R? {
    return if (first!=null&&second!=null){
        block(first, second)
    }else{
        null
    }
}

inline fun <A, B, C, R> notNull(first: A?, second: B?, third: C?, block: (A, B, C) -> R): R? {
    return if (first!=null&&second!=null&&third!=null){
        block(first, second, third)
    }else{
        null
    }
}

fun color(fraction: Float, startColor: Int, endColor: Int):Int{
    val redCurrent: Int
    val blueCurrent: Int
    val greenCurrent: Int
    val alphaCurrent: Int
    val redStart = Color.red(startColor)
    val blueStart = Color.blue(startColor)
    val greenStart = Color.green(startColor)
    val alphaStart = Color.alpha(startColor)
    val redEnd = Color.red(endColor)
    val blueEnd = Color.blue(endColor)
    val greenEnd = Color.green(endColor)
    val alphaEnd = Color.alpha(endColor)
    val redDifference = redEnd - redStart
    val blueDifference = blueEnd - blueStart
    val greenDifference = greenEnd - greenStart
    val alphaDifference = alphaEnd - alphaStart
    redCurrent = (redStart + fraction * redDifference).toInt()
    blueCurrent = (blueStart + fraction * blueDifference).toInt()
    greenCurrent = (greenStart + fraction * greenDifference).toInt()
    alphaCurrent = (alphaStart + fraction * alphaDifference).toInt()
    return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent)
}
