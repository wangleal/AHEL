package wang.leal.ahel.http.download

object Downloader {
    @JvmStatic
    fun url(url:String):DownloadService{
        return DownloadService(url)
    }
}