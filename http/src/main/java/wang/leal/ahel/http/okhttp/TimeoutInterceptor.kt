package wang.leal.ahel.http.okhttp

import okhttp3.Interceptor
import okhttp3.Response
import wang.leal.ahel.http.api.annotation.Timeout
import wang.leal.ahel.http.api.create.Invocation
import java.lang.reflect.Method

class TimeoutInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val tag = request.tag(Invocation::class.java)
        val method: Method? = tag?.method()
        val timeout: Timeout? = method?.getAnnotation(Timeout::class.java)
        timeout?.let {
            if (it.duration>0){
                return chain.withConnectTimeout(it.duration, it.unit)
                    .withReadTimeout(it.duration,it.unit)
                    .withWriteTimeout(it.duration,it.unit)
                    .proceed(request)
            }
        }
        return chain.proceed(request)
    }
}