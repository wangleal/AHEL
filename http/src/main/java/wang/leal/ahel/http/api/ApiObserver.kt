package wang.leal.ahel.http.api

import wang.leal.ahel.http.api.converter.ApiException
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

open class ApiObserver<T> : Observer<T> {
    final override fun onSubscribe(d: Disposable) {
        onStart(d)
    }
    final override fun onNext(data: T) {
        try {
            onSuccess(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    final override fun onError(e: Throwable) {
        try {
            if (e is ApiException) {
                onApiError(e.code(), e.message(), e.data())
            } else {
                onFailure(e)
            }
        } catch (failureError: Exception) {
            failureError.printStackTrace()
        } finally {
            try {
                onFinal()
            } catch (finalError: Exception) {
                finalError.printStackTrace()
            }
        }
    }

    final override fun onComplete() {
        try {
            onFinal()
        } catch (finalError: Exception) {
            finalError.printStackTrace()
        }
    }

    open fun onStart(disposable: Disposable){}

    //Http响应成功并且服务端code返回成功
    open fun onSuccess(data: T) {}

    //有响应但是业务异常
    open fun onApiError(errNo: Int, errMsg: String?, data: String?) {}

    //超时，解析等等返回的各种错误
    open fun onFailure(e: Throwable) {}

    //无论成功或者失败，最终都会进入这里
    open fun onFinal() {}
}