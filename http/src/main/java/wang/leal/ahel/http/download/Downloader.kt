package wang.leal.ahel.http.download

object Downloader {

    fun url(url:String):DownloadService{
        return DownloadService(url)
    }
}