package wang.leal.ahel.http.api.service.okhttp;

import wang.leal.ahel.http.api.ApiService;
import wang.leal.ahel.http.api.observable.CallExecuteObservable;
import wang.leal.ahel.http.api.observable.ExceptionObservable;
import wang.leal.ahel.http.api.response.ResponseHelper;
import wang.leal.ahel.http.api.service.PostService;
import wang.leal.ahel.http.api.service.entity.FileEntity;
import wang.leal.ahel.http.exception.HttpException;
import wang.leal.ahel.http.json.GsonManager;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpPostService extends PostService {
    OkHttpPostService(String url) {
        super(url);
    }

    @Override
    public <T> Observable<T> observable(final Class<T> clazz) {
        Request.Builder builder = new Request.Builder();
        if (headerMap!=null&&headerMap.size()>0){
            for (Map.Entry<String,String> entry:headerMap.entrySet()){
                if (entry!=null){
                    builder.addHeader(entry.getKey(),entry.getValue());
                }
            }
        }

        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl!=null){
            if (queryMap!=null&&queryMap.size()>0){
                HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
                for (Map.Entry<String,String> entry:queryMap.entrySet()){
                    if (entry!=null){
                        httpUrlBuilder.addQueryParameter(entry.getKey(),entry.getValue());
                    }
                }
                httpUrl = httpUrlBuilder.build();
            }
            builder.url(httpUrl);
        }else {
            return ExceptionObservable.nullable("Url is illegal.");
        }
        RequestBody requestBody;
        if (body!=null){
            String json = GsonManager.gson().toJson(body);
            requestBody = RequestBody.create(MediaType.parse("application/json"),json);
        }else {
            if (fileList.size()==0){
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                for (Map.Entry<String,String> fieldEntry:fieldMap.entrySet()){
                    if (fieldEntry!=null){
                        bodyBuilder.add(fieldEntry.getKey(),fieldEntry.getValue());
                    }
                }
                requestBody = bodyBuilder.build();
            }else {
                MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
                multipartBuilder.setType(MultipartBody.FORM);
                if (fieldMap.size()>0){
                    for (Map.Entry<String,String> fieldEntry:fieldMap.entrySet()){
                        if (fieldEntry!=null){
                            multipartBuilder.addFormDataPart(fieldEntry.getKey(),fieldEntry.getValue());
                        }
                    }
                }
                if (fileList.size()>0){
                    for (FileEntity fileEntry:fileList){
                        File file = fileEntry.getFile();
                        RequestBody body = RequestBody.create(MediaType.parse(fileEntry.getMime()),file);
                        multipartBuilder.addFormDataPart(fileEntry.getName(),file.getName(),body);
                    }
                }
                requestBody = multipartBuilder.build();
            }
        }

        Request request = builder.post(requestBody).build();
        Call call = ApiService.client().newCall(request);
        Observable<Response> responseObservable = new CallExecuteObservable(call);
        return  responseObservable.map(response -> {
            if (response!=null){
                if (response.isSuccessful()){
                    String json = "";
                    if (response.body()!=null){
                        json = response.body().string();
                    }
                    return ResponseHelper.dealResult(json,clazz);
                }else {
                    String body = "";
                    if (response.body()!=null){
                        body = response.body().string();
                    }
                    throw new HttpException(response.code(),response.message(),body);
                }
            }
            return null;
        });
    }
}
