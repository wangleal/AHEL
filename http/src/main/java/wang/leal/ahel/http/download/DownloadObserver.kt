package wang.leal.ahel.http.download

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

open class DownloadObserver<T> : Observer<T> {
    final override fun onSubscribe(d: Disposable?) {

    }

    final override fun onNext(data: T) {
        onSuccess(data)
    }

    final override fun onError(e: Throwable) {
        try {
            onFailure(e)
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

    //Http响应成功
    protected fun onSuccess(data: T) {}

    //各种错误
    protected fun onFailure(e: Throwable) {}

    //无论成功或者失败，最终都会进入这里
    protected fun onFinal() {}
}