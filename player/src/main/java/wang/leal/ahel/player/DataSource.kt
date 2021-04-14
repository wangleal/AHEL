package wang.leal.ahel.player

import android.net.Uri
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import java.io.File

class DataSource private constructor(private val uri: Uri){

    fun getUri():Uri{
        return uri
    }

    companion object{
        fun createByUrl(url:String): DataSource {
            return createByUri(Uri.parse(url))
        }

        fun createByUri(uri:Uri): DataSource {
            return DataSource(uri)
        }

        fun createByAsset(path:String): DataSource {
            return when {
                path.startsWith("/") -> {
                    createByUri(Uri.parse("file:///android_asset$path"))
                }
                else -> {
                    createByUri(Uri.parse("file:///android_asset/$path"))
                }
            }
        }

        fun createByRaw(rawId:Int): DataSource {
            return createByUri(RawResourceDataSource.buildRawResourceUri(rawId))
        }

        fun createByFile(file:File): DataSource {
            return createByUri(Uri.fromFile(file))
        }

        fun createByFile(path:String): DataSource {
            return createByUri(Uri.fromFile(File(path)))
        }
    }
}