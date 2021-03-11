package wang.leal.ahel.http.upload

import wang.leal.ahel.http.api.converter.ApiException
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

open class UploadObserver<T> : Observer<T> {
    final override fun onSubscribe(d: Disposable) {}
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

    //Http响应成功并且服务端code返回成功
    protected fun onSuccess(data: T) {}

    //Http响应成功但是服务端code报告异常
    protected fun onApiError(errNo: Int, errMsg: String?, data: String?) {}

    //Http或者解析等等返回的各种错误
    protected fun onFailure(e: Throwable?) {}

    //无论成功或者失败，最终都会进入这里
    protected fun onFinal() {}
}