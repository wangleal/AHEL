package wang.leal.ahel.extension

import java.security.MessageDigest

fun String.md5(): String {
    val instance:MessageDigest = MessageDigest.getInstance("MD5")
    val digest:ByteArray = instance.digest(this.toByteArray())
    val sb = StringBuffer()
    for (byte in digest) {
        val i :Int = byte.toInt() and 0xff
        val hexString = Integer.toHexString(i)
        if (hexString.length < 2) {
            sb.append("0")
        }
        sb.append(hexString)
    }
    return sb.toString()
}

fun String.hasChinese(): Boolean {
    val chinese = Regex("[\u4e00-\u9fa5]")
    for (i in 0 until length) {
        val temp = substring(i, i + 1)
        if (temp.matches(chinese)) {
            return true
        }
    }
    return false
}

fun String.hasNumber(): Boolean {
    return matches(Regex(".*[0-9].*"))
}

fun String.hasEnglish(): Boolean {
    return matches(Regex(".*[a-zA-z].*"))
}

fun String.isAllNumber(): Boolean {
    return matches(Regex("[0-9]+"))
}

fun String.isAllEnglish(): Boolean {
    return matches(Regex("[a-zA-Z]+"))
}

fun String.isAllChinese(): Boolean {
    return matches(Regex("[\u4e00-\u9fa5]+"))
}