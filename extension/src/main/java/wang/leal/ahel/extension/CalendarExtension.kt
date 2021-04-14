package wang.leal.ahel.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.today(locale: Locale = Locale.CHINA): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd",locale)
    return sdf.format(this.time)
}

fun Calendar.yesterday(locale: Locale = Locale.CHINA): String {
    this.add(Calendar.DATE, -1)
    val sdf = SimpleDateFormat("yyyy-MM-dd",locale)
    return sdf.format(this.time)
}