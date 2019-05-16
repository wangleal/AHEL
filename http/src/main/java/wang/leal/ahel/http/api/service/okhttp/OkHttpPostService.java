package wang.leal.ahel.http.api.service.okhttp;

import com.google.gson.Gson;
import wang.leal.ahel.http.api.ApiCode;
import wang.leal.ahel.http.api.exception.ApiException;
import wang.leal.ahel.http.api.observable.CallExecuteObservable;
import wang.leal.ahel.http.api.observable.ExceptionObservable;
import wang.leal.ahel.http.api.service.PostService;
import wang.leal.ahel.http.api.service.entity.FileEntity;
import wang.leal.ahel.http.api.service.entity.Result;
import wang.leal.ahel.http.exception.HttpException;
import wang.leal.ahel.http.json.GsonManager;
import wang.leal.ahel.http.okhttp.OkHttpManager;
import wang.leal.ahel.http.utils.Utils;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpPostService extends PostService {
    private OkHttpClient client = OkHttpManager.getApiOkHttpClient();
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
            if (fieldMap.size()>0&&fileList.size()==0){
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
        Call call = client.newCall(request);
        Observable<Response> responseObservable = new CallExecuteObservable(call);
        return  responseObservable.map(response -> {
            if (response!=null){
                if (response.isSuccessful()){
                    String json = "";
                    if (response.body()!=null){
                        json = response.body().string();
                    }
                    Gson gson = GsonManager.gson();
                    Class returnRawType = Utils.getRawType(clazz);
                    Result<Object> result = gson.fromJson(json, Utils.getParameterized(null,Result.class, Object.class));
                    if (result.getCode()==ApiCode.CODE_SUCCESS){
                        if (returnRawType==Result.class){
                            return gson.fromJson(json,clazz);
                        }else {
                            return gson.fromJson(gson.toJson(result.getData()),clazz);
                        }
                    }else {
                        throw new ApiException(result.getCode(), result.getMessage(),json);
                    }
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
