package wang.leal.ahel.bus

import com.google.gson.Gson

object Converter {
    private val gson:Gson = Gson()
    @Suppress("UNCHECKED_CAST")
    fun <T> convert(value: String, clazz: Class<T>):T{
        if (clazz === Int::class.javaPrimitiveType || clazz === Int::class.java) {
            return value.toInt() as T
        } else if (clazz === String::class.java) {
            return value as T
        } else if (clazz === Boolean::class.javaPrimitiveType || clazz === Boolean::class.java) {
            return value.toBoolean() as T
        } else if (clazz === Byte::class.javaPrimitiveType || clazz === Byte::class.java) {
            return value.toByte() as T
        } else if (clazz === Char::class.javaPrimitiveType || clazz === Char::class.java) {
            return value as T
        } else if (clazz === Double::class.javaPrimitiveType || clazz === Double::class.java) {
            return value.toDouble() as T
        } else if (clazz === Float::class.javaPrimitiveType || clazz === Float::class.java) {
            return value.toFloat() as T
        } else if (clazz === Long::class.javaPrimitiveType || clazz === Long::class.java) {
            return value.toLong() as T
        } else if (clazz === Short::class.javaPrimitiveType || clazz === Short::class.java) {
            return value.toShort() as T
        } else if (clazz === Unit::class.java) {
            return Unit as T
        } else if (clazz === Void::class.java) {
            return null as T
        } else {
            return gson.fromJson(value,clazz)
        }
    }

    fun convert(value: Any):String{
        val clazz = value.javaClass
        if (clazz === Int::class.javaPrimitiveType || clazz === Int::class.java) {
            return value.toString()
        } else if (clazz === String::class.java) {
            return value.toString()
        } else if (clazz === Boolean::class.javaPrimitiveType || clazz === Boolean::class.java) {
            return value.toString()
        } else if (clazz === Byte::class.javaPrimitiveType || clazz === Byte::class.java) {
            return value.toString()
        } else if (clazz === Char::class.javaPrimitiveType || clazz === Char::class.java) {
            return value.toString()
        } else if (clazz === Double::class.javaPrimitiveType || clazz === Double::class.java) {
            return value.toString()
        } else if (clazz === Float::class.javaPrimitiveType || clazz === Float::class.java) {
            return value.toString()
        } else if (clazz === Long::class.javaPrimitiveType || clazz === Long::class.java) {
            return value.toString()
        } else if (clazz === Short::class.javaPrimitiveType || clazz === Short::class.java) {
            return value.toString()
        } else if (clazz === Unit::class.java||clazz===Void::class.java) {
            return ""
        } else {
            return gson.toJson(value)
        }
    }
}