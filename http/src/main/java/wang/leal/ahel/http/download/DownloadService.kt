package wang.leal.ahel.http.download

import wang.leal.ahel.http.okhttp.OkHttpManager
import io.reactivex.rxjava3.core.Observable
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class DownloadService(val url: String) {

    fun download(file: File): Observable<File> {
        return Observable.create {
            val request: Request = Request.Builder().url(url).build()
            var inputStream:InputStream? = null
            try {
                val response = OkHttpManager.downloadOkHttpClient.newCall(request)
                        .execute()
                inputStream = response.body?.byteStream()
                if (!file.exists()) {
                    file.createNewFile()
                }
                FileOutputStream(file).use { output ->
                    val buffer = ByteArray(4 * 1024)
                    var read: Int
                    inputStream?.let { input ->
                        while (input.read(buffer).also { length -> read = length } != -1) {
                            output.write(buffer, 0, read)
                        }
                    }
                    output.flush()
                }
                it.onNext(file)
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
                e.printStackTrace()
            } finally {
                inputStream?.close()
            }
            if (file.exists()&&file.length()>0){
                it.onNext(file)
                it.onComplete()
            }
        }
    }
}