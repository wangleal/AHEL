package wang.leal.ahel.extension

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.toDecimal(length:Int): Double {
    val decimal = BigDecimal(this)
    return decimal.setScale(length,RoundingMode.HALF_UP).toDouble()
}