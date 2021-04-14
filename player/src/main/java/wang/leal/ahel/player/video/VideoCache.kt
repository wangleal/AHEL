package wang.leal.ahel.player.video

import android.content.Context
import android.net.Uri
import com.danikula.videocache.HttpProxyCacheServer

internal object VideoCache {

    private var builder:HttpProxyCacheServer.Builder? = null
    private var proxy:HttpProxyCacheServer? = null
    fun initialize(context: Context){
        builder = HttpProxyCacheServer.Builder(context.applicationContext)
                .maxCacheSize(50 * 1024 * 1024)

    }

    @Synchronized
    fun cache(uri: Uri):Uri{
        if (proxy ==null){
            proxy = builder?.build()
        }
        val url = uri.toString()
        return if (url.startsWith("http://")||url.startsWith("https://")){
            Uri.parse(proxy?.getProxyUrl(url)?:url)
        }else{
            uri
        }
    }

    @Synchronized
    fun release(){
        proxy?.shutdown()
        proxy = null
    }

}