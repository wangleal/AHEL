package wang.leal.ahel.sample.http

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import wang.leal.ahel.R
import wang.leal.ahel.http.api.Api
import wang.leal.ahel.http.api.ApiObserver
import wang.leal.ahel.http.api.subscribe
import wang.leal.ahel.rxlifecycle.bindOnDestroy
import java.io.File

class HttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)
        findViewById<Button>(R.id.bt_test).setOnClickListener {
            testDispose()
        }
    }

    private var disposable:Disposable? = null
    private fun testDispose() {
        disposable = TestObservable()
                .doOnDispose {
                    Log.e("Sample", "doOnDispose 1")
                }
                .subscribeOn(Schedulers.io())
                .bindOnDestroy(this)
                .doOnDispose {
                    Log.e("Sample", "doOnDispose 2")
                }
                .doOnComplete {
                    Log.e("Sample", "complete")
                }
                .doOnNext {
                    Log.e("Sample", "next:$it")
                }
                .subscribe()
    }

    private fun testErrorNull() {
        Api.create(Sample::class.java)
                .testError("http://www.baidu.com/error/null")
                .subscribe(object : ApiObserver<Int>() {
                    override fun onSuccess(data: Int) {
                        Log.e("Sample", "onSuccess:$data")
                    }

                    override fun onApiError(errNo: Int, errMsg: String?, data: String?) {
                        Log.e("Sample", "onApiError: code:$errNo,message:$errMsg,data:$data")
                    }

                    override fun onFailure(e: Throwable) {
                        Log.e("Sample", "onFailure")
                        e.printStackTrace()
                    }
                })
    }

    private fun testError() {
        Api.create(Sample::class.java)
                .testError("http://www.baidu.com/error/")
                .subscribe(object : ApiObserver<Int>() {
                    override fun onSuccess(data: Int) {
                        Log.e("Sample", "onSuccess:$data")
                    }

                    override fun onApiError(errNo: Int, errMsg: String?, data: String?) {
                        Log.e("Sample", "onApiError: code:$errNo,message:$errMsg,data:$data")
                    }

                    override fun onFailure(e: Throwable) {
                        Log.e("Sample", "onFailure")
                        e.printStackTrace()
                    }
                })
    }

    private fun testUpload() {
        val partMap = mutableMapOf<String, RequestBody>()
        val body: RequestBody = File("").asRequestBody("Image/*".toMediaTypeOrNull())
        partMap["1"] = body
        partMap["2"] = body
        partMap["3"] = body
        Api.create(Sample::class.java)
                .testMulti("http://www.baidu.com/", partMap)
                .subscribe(object : ApiObserver<Int>() {
                    override fun onSuccess(data: Int) {
                        Log.e("Sample", "onSuccess:$data")
                    }

                    override fun onFailure(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }

    private fun testDeleteChained() {
        Api.delete("chained").header("haha", "hehe").query("a", "b")
                .observable(Int::class.java)
                .subscribe(object : ApiObserver<Int>() {
                    override fun onSuccess(data: Int) {
                        Log.e("Sample", "onSuccess:$data")
                    }
                })
    }

    private fun testChained() {
        Api.get("chained").header("haha", "hehe").query("a", "b")
                .observable(Int::class.java)
                .subscribe(object : ApiObserver<Int>() {
                    override fun onSuccess(data: Int) {
                        Log.e("Sample", "onSuccess:$data")
                    }
                })
    }

    private fun testEmpty() {
        val result = Result("jitao", 30, false)
        Api.create(Sample::class.java)
                .testEmptyResult("http://www.baidu.com/empty/", result)
                .subscribe()
    }

    private fun testEmptyData() {
        val result = Result("jitao", 30, false)
        Api.create(Sample::class.java)
                .testEmptyData("http://www.baidu.com/empty/data", result)
                .subscribe {
                    Log.e("Sample", "$it")
                }
    }

    private fun testBody() {
        val result = Result("jitao", 30, false)
        Api.create(Sample::class.java)
                .testBody("http://www.baidu.com/result/", result)
                .subscribe(object : ApiObserver<Result>() {
                    override fun onSuccess(data: Result) {
                        Log.e("Sample", "onSuccess:$data")
                    }

                    override fun onFailure(e: Throwable) {
                        e?.printStackTrace()
                    }
                })
    }

    private fun testFlatMap() {
        Api.create(Sample::class.java)
                .testResult("http://www.baidu.com/result/", "eyJraWQiOiJhY2NvdW50IiwiYWxnIjoiUlMyNTYiLCJ6aXAiOiJHWklQIn0.H4sIAAAAAAAAACWMQQrCMBBF7zLrVGaa5M80N3DnostsKg0YQZAaQSm9u1W3_733V7q2Somgap4jVDwr-8AKcvR4nne2ZmqXusynaWnv45wpZTJIhCgsDkAml6m87nUpY72Vn9BzLx37TmwUnzxSwMEwZNr23zo1SgIBa2CG-8b_AWrG2D6lTs21lwAAAA.ZrS4eC5dNdQT6Agy2noo6DWCtM-r22UFmzkFqIhzX5T9HOX53mSbl1K7sHqNBfgpKnsaxAAq78q5jRrx4yHb2mUZqab5K20lx8w0SDb55iEo0bLGA42SKVWFcb2Ve89V_2SrFF3lH9SJqfjr8APMgrg4a-0W1Cf-JqvLsBOhFKLoafTubisSB8eiWDtvNRz5qTkGV5rVirkVE_NE2o8fJkdm6sQKiL02VpmAwx3ncMGp1FSxxjrw0YyUPqOB-orotDXSHWT-tuS2Etz8QIEZCSBfxNCcuSN4UYv-m_vVTSHPky_ZylWPVXCYsxTxviI-IywPQZLCQfUHRmiaZDkOZg", "1")
                .flatMap {
                    Log.e("Sample", "flatmap 1:" + Thread.currentThread().name)
                    Api.create(Sample::class.java).testPath("http://www.baidu.com/{a}/{b}/", "1", "2")
                }
                .observeOn(Schedulers.io())
                .flatMap {
                    Log.e("Sample", "flatmap 2:" + Thread.currentThread().name)
                    Api.create(Sample::class.java).test("http://www.baidu.com", "3")
                }
                .subscribe(object : ApiObserver<Int>() {
                    override fun onSuccess(data: Int) {
                        Log.e("Sample", "subscribe:" + Thread.currentThread().name)
                        super.onSuccess(data)
                    }
                })
    }

    private fun testResult() {
        Api.create(Sample::class.java)
                .testResult("http://www.baidu.com/result/", "1", "2")
                .subscribe(object : ApiObserver<Result>() {
                    override fun onSuccess(data: Result) {
                        findViewById<TextView>(R.id.tv_result).text = data.toString()
                        Log.e("Sample", "onSuccess:$data")
                    }

                    override fun onApiError(errNo: Int, errMsg: String?, data: String?) {
                        Log.e("Sample", "onApiError:$data")
                    }

                    override fun onFailure(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onFinal() {
                        Log.e("Sample", "onFinal")
                    }
                })
    }

    private fun testPath() {
        Api.create(Sample::class.java)
                .testPath("http://www.baidu.com/{a}/{b}/", "1", "2")
                .subscribe(object : ApiObserver<Int>() {
                    override fun onSuccess(data: Int) {
                        Log.e("Sample", "onSuccess:$data")
                    }

                    override fun onApiError(errNo: Int, errMsg: String?, data: String?) {
                        Log.e("Sample", "onApiError:$data")
                    }

                    override fun onFailure(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onFinal() {
                        Log.e("Sample", "onFinal")
                    }
                })
    }

    private fun test() {
        Api.create(Sample::class.java)
                .test("http://www.baidu.com", "1")
                .subscribe(object : ApiObserver<Int>() {
                    override fun onSuccess(data: Int) {
                        Log.e("Sample", "onSuccess:$data")
                    }

                    override fun onApiError(errNo: Int, errMsg: String?, data: String?) {
                        Log.e("Sample", "onApiError:$data")
                    }

                    override fun onFailure(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onFinal() {
                        Log.e("Sample", "onFinal")
                    }
                })
    }

    private fun testQuery() {
        Api.create(Sample::class.java)
                .testQuery("http://www.baidu.com/empty/data", "hehe", "haha", "heihei")
                .subscribe {
                    Log.e("Sample", "$it")
                }
    }
}