package wang.leal.ahel.http.upload

object Uploader {
    @JvmStatic
    fun url(url:String): UploadService {
        return UploadService(url)
    }
}