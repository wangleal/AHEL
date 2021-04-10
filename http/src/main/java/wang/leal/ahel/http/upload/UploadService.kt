package wang.leal.ahel.http.upload

import wang.leal.ahel.http.api.converter.ResponseHelper
import wang.leal.ahel.http.api.create.HttpException
import wang.leal.ahel.http.okhttp.OkHttpManager
import io.reactivex.rxjava3.core.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class UploadService(val url: String) {
    private val partList = mutableListOf<MultipartBody.Part>()

    fun image(name: String, image: File):UploadService{
        return file(name, image, "image/*")
    }

    fun video(name: String, video: File):UploadService{
        return file(name, video, "video/*")
    }

    fun audio(name: String, audio: File):UploadService{
        return file(name, audio, "audio/*")
    }

    fun file(name: String, file: File):UploadService{
        return file(name, file, "")
    }

    private fun file(name: String, file: File, mimeType: String):UploadService{
        if (!file.exists()){
            return this
        }
        val requestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())
        val part = if (name.isBlank()){
            MultipartBody.Part.create(requestBody)
        }else{
            MultipartBody.Part.createFormData(name, file.name, requestBody)
        }
        partList.add(part)
        return this
    }


    fun field(name: String, value: String):UploadService{
        if (name.isBlank()||value.isBlank()){
            return this
        }
        val part = MultipartBody.Part.createFormData(name, value)
        partList.add(part)
        return this
    }

    fun <T> upload(clazz: Class<T>): Observable<T> {
        return Observable.create{
            val multipartBuilder: MultipartBody.Builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
            partList.forEach { part ->
                multipartBuilder.addPart(part)
            }
            val requestBody = multipartBuilder.build()
            val request: Request = Request.Builder().url(url).post(requestBody).build()
            val response = OkHttpManager.uploadOkHttpClient.newCall(request).execute()
            if (response.isSuccessful){
                it.onNext(ResponseHelper.convert(response.body,clazz))
            }else{
                it.onError(HttpException(response.code, response.message))
            }
        }
    }

}