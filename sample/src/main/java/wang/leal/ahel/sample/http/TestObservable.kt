package wang.leal.ahel.sample.http

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class TestObservable: Observable<Int>() {

    override fun subscribeActual(observer: Observer<in Int>) {
        val testDisposable = TestDisposable()
        observer.onSubscribe(testDisposable)
        while (true){
            Thread.sleep(1000)
            val value = (0..1000).random()
            Log.e("Sample", "test while value:$value")
            observer.onNext(value)
        }
    }

    class TestDisposable:Disposable{
        private var isDisposed = false
        override fun dispose() {
            Log.e("Sample", "test dispose")
            isDisposed = true
            printCallStack()
        }

        override fun isDisposed(): Boolean {
            return isDisposed
        }
        private fun printCallStack() {
            val ex = Throwable()
            ex.printStackTrace()
        }

    }



}