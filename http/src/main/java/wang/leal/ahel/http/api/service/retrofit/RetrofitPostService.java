package wang.leal.ahel.http.api.service.retrofit;

import com.google.gson.Gson;

import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.response.Origin;
import wang.leal.ahel.http.api.service.PostService;
import wang.leal.ahel.http.api.service.entity.FileEntity;
import wang.leal.ahel.http.json.gson.GsonManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by wang leal on 2017/8/29.
 */

public class RetrofitPostService extends PostService {

    RetrofitPostService(String url) {
        super(url);
    }

    @Override
    public <T> Observable<T> observable(final Class<T> clazz) {
        if (Origin.class.isAssignableFrom(clazz)){
            return observableOrigin(clazz);
        }else {
            return observableString(clazz);
        }
    }

    private <T> Observable<T> observableOrigin(final Class<T> clazz){
        Gson gson = GsonManager.gson();
        Function<Object,T> function = origin ->gson.fromJson(gson.toJson(origin),clazz);

        if (body!=null){
            Observable<Object> objectObservable = Api.create(PostApi.class).bodyOrigin(url,headerMap,body,queryMap);
            return objectObservable.map(function);
        }

        if (fieldMap.size()>0&&fileList.size()==0){
            Observable<Object> objectObservable = Api.create(PostApi.class).fieldOrigin(url,headerMap,fieldMap,queryMap);
            return objectObservable.map(function);
        }

        Map<String, RequestBody> partMap = new HashMap<>();
        if (fieldMap.size()>0){
            for (Map.Entry<String,String> fieldEntry:fieldMap.entrySet()){
                if (fieldEntry!=null){
                    partMap.put(fieldEntry.getKey(),RequestBody.create(MediaType.parse("text/plain"),fieldEntry.getValue()));
                }
            }
        }
        if (fileList.size()>0){
            for (FileEntity fileEntry:fileList){
                File file = fileEntry.getFile();
                RequestBody body = RequestBody.create(MediaType.parse(fileEntry.getMime()),file);
                partMap.put(fileEntry.getName()+"\"; filename=\""+file.getName()+"\"",body);
            }
        }
        if (partMap.size()==0){
            Observable<Object> objectObservable = Api.create(PostApi.class).postOrigin(url,headerMap,queryMap);
            return objectObservable.map(function);
        }else {
            Observable<Object> objectObservable = Api.create(PostApi.class).partOrigin(url,headerMap,partMap,queryMap);
            return objectObservable.map(function);
        }
    }

    private <T> Observable<T> observableString(final Class<T> clazz){
        Function<String,T> function = s -> GsonManager.gson().fromJson(s,clazz);

        if (body!=null){
            Observable<String> stringObservable = Api.create(PostApi.class).body(url,headerMap,body,queryMap);
            return stringObservable.map(function);
        }

        if (fieldMap.size()>0&&fileList.size()==0){
            Observable<String> stringObservable = Api.create(PostApi.class).field(url,headerMap,fieldMap,queryMap);
            return stringObservable.map(function);
        }

        Map<String, RequestBody> partMap = new HashMap<>();
        if (fieldMap.size()>0){
            for (Map.Entry<String,String> fieldEntry:fieldMap.entrySet()){
                if (fieldEntry!=null){
                    partMap.put(fieldEntry.getKey(),RequestBody.create(MediaType.parse("text/plain"),fieldEntry.getValue()));
                }
            }
        }
        if (fileList.size()>0){
            for (FileEntity fileEntry:fileList){
                File file = fileEntry.getFile();
                RequestBody body = RequestBody.create(MediaType.parse(fileEntry.getMime()),file);
                partMap.put(fileEntry.getName()+"\"; filename=\""+file.getName()+"\"",body);
            }
        }
        if (partMap.size()==0){
            Observable<String> stringObservable = Api.create(PostApi.class).post(url,headerMap,queryMap);
            return stringObservable.map(function);
        }else {
            Observable<String> stringObservable = Api.create(PostApi.class).part(url,headerMap,partMap,queryMap);
            return stringObservable.map(function);
        }
    }

    public interface PostApi{
        @POST
        Observable<String> post(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> queryMap);

        @POST
        @FormUrlEncoded
        Observable<String> field(@Url String url, @HeaderMap Map<String, String> headerMap, @FieldMap Map<String, String> fieldMap, @QueryMap Map<String, String> queryMap);

        @POST
        Observable<String> body(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object body, @QueryMap Map<String, String> queryMap);

        @POST
        @Multipart
        Observable<String> part(@Url String url, @HeaderMap Map<String, String> headerMap, @PartMap Map<String, RequestBody> partMap, @QueryMap Map<String, String> queryMap);

        @POST
        Observable<Object> postOrigin(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> queryMap);

        @POST
        @FormUrlEncoded
        Observable<Object> fieldOrigin(@Url String url, @HeaderMap Map<String, String> headerMap, @FieldMap Map<String, String> fieldMap, @QueryMap Map<String, String> queryMap);

        @POST
        Observable<Object> bodyOrigin(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object body, @QueryMap Map<String, String> queryMap);

        @POST
        @Multipart
        Observable<Object> partOrigin(@Url String url, @HeaderMap Map<String, String> headerMap, @PartMap Map<String, RequestBody> partMap, @QueryMap Map<String, String> queryMap);
    }
}
