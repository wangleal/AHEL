@file:Suppress("UNCHECKED_CAST")
package wang.leal.ahel.http.api.service

import wang.leal.ahel.http.api.create.ServiceMethod
import io.reactivex.rxjava3.core.Observable
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

object CreateService {
    private val serviceMethodCache: ConcurrentHashMap<Method, ServiceMethod<Any>?> = ConcurrentHashMap()
    private fun loadServiceMethod(method: Method): ServiceMethod<Any>?{
        var result: ServiceMethod<Any>? = serviceMethodCache[method]
        if (result != null) return result
        synchronized(serviceMethodCache) {
            result = serviceMethodCache[method]
            if (result == null) {
                result = ServiceMethod.parseAnnotations(method)
                serviceMethodCache[method] = result
            }
        }
        return result
    }

    fun <T>create(service: Class<T>):T{
        val invocationHandler = InvocationHandler { _, method, args ->
            try {
                loadServiceMethod(method)?.invoke(args)
            }catch (e:Exception){
                Observable.error<Exception>(e)
            }
        }
        return Proxy.newProxyInstance(
            service.classLoader, arrayOf<Class<*>>(service),
            invocationHandler) as T
    }
}