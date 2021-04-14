package wang.leal.ahel.extension

import java.math.BigDecimal
import java.math.RoundingMode

fun Float.toDecimal(length:Int): Float {
    val decimal = BigDecimal(this.toDouble())
    return decimal.setScale(length,RoundingMode.HALF_UP).toString().toFloat()
}