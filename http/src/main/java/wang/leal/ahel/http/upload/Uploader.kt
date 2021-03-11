package wang.leal.ahel.http.upload

object Uploader {
    fun url(url:String): UploadService {
        return UploadService(url)
    }
}