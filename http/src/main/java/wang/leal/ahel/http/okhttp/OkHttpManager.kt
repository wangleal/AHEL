package wang.leal.ahel.http.okhttp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpManager {
    val apiOkHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder.writeTimeout(60, TimeUnit.SECONDS)
            builder.dns(DNSManager())
            builder.addInterceptor(TimeoutInterceptor())
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addNetworkInterceptor(httpLoggingInterceptor)
            return builder.build()
        }

    val uploadOkHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder.writeTimeout(60, TimeUnit.SECONDS)
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            builder.addNetworkInterceptor(httpLoggingInterceptor)
            return builder.build()
        }

    val downloadOkHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(60 * 2, TimeUnit.SECONDS)
            builder.readTimeout(60 * 2, TimeUnit.SECONDS)
            builder.writeTimeout(60 * 2, TimeUnit.SECONDS)
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            builder.addNetworkInterceptor(httpLoggingInterceptor)
            return builder.build()
        }
}