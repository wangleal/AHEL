package wang.leal.ahel.http.okhttp

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class TimeoutInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val timeout = request.header("Request-Timeout")?.toInt()
        val requestBuilder = request.newBuilder()
        timeout?.let {
            if (it>0){
                requestBuilder.removeHeader("Request-Timeout")
                return chain.withConnectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .withReadTimeout(timeout,TimeUnit.MILLISECONDS)
                    .withWriteTimeout(timeout,TimeUnit.MILLISECONDS)
                    .proceed(requestBuilder.build())
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}