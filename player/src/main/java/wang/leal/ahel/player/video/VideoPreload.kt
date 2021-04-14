package wang.leal.ahel.player.video

import android.net.Uri
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InputStream
import java.net.URL

internal object VideoPreload {
    private var compositeDisposable:CompositeDisposable? = null
    private var caching = mutableListOf<String>()
    @Synchronized
    fun preload(videoUrl: String) {
        if (caching.contains(videoUrl)) {
            return
        }

        caching.add(videoUrl)
        val disposable = Observable.create<Unit> {
            val proxyUrl = VideoCache.cache(Uri.parse(videoUrl)).toString()
            if (proxyUrl != videoUrl) {
                var inputStream: InputStream? = null
                try {
                    inputStream = URL(proxyUrl).openStream()
                    val buffer = ByteArray(1024)
                    var len = inputStream.read(buffer, 0, buffer.size)
                    while (isCaching(videoUrl) && len != -1) {
                        len = inputStream.read(buffer, 0, buffer.size)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        inputStream?.close()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                    cacheCompleted(videoUrl)
                }
            }
        }.subscribeOn(Schedulers.io()).subscribe()
        compositeDisposable = (compositeDisposable ?:CompositeDisposable()).also {
            it.add(disposable)
        }
    }

    @Synchronized
    private fun isCaching(videoUrl: String) = caching.contains(videoUrl)

    @Synchronized
    private fun cacheCompleted(videoUrl: String) {
        caching.remove(videoUrl)
    }

    @Synchronized
    fun release() {
        caching.clear()
        compositeDisposable?.dispose()
        compositeDisposable =null
    }
}