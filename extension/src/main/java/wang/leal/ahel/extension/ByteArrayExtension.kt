package wang.leal.ahel.extension

import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

fun ByteArray.md5(): String {
    val instance:MessageDigest = MessageDigest.getInstance("MD5")
    val digest:ByteArray = instance.digest(this)
    return digest.toHexString()
}

fun ByteArray.toHexString(): String {
    val sb = StringBuffer()
    for (byte in this) {
        val i :Int = byte.toInt() and 0xff
        val hexString = Integer.toHexString(i)
        if (hexString.length < 2) {
            sb.append("0")
        }
        sb.append(hexString)
    }
    return sb.toString()
}

fun ByteArray.concat(byteArray: ByteArray): ByteArray {
    val bytes = ByteArray(size + byteArray.size)
    System.arraycopy(this, 0, bytes, 0, size)
    System.arraycopy(byteArray, 0, bytes, size,
            byteArray.size)
    return bytes
}

fun ByteArray.subByteArray(start: Int, end: Int): ByteArray {
    return copyOfRange(start, end)
}

fun ByteArray.subByteArray(start: Int): ByteArray {
    return copyOfRange(start, this.size)
}