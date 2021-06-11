package wang.leal.ahel.http.api.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.annotation.Body;
import wang.leal.ahel.http.api.annotation.FieldMap;
import wang.leal.ahel.http.api.annotation.FormUrlEncoded;
import wang.leal.ahel.http.api.annotation.HeaderMap;
import wang.leal.ahel.http.api.annotation.Multipart;
import wang.leal.ahel.http.api.annotation.POST;
import wang.leal.ahel.http.api.annotation.PartMap;
import wang.leal.ahel.http.api.annotation.QueryMap;
import wang.leal.ahel.http.api.annotation.Url;
import wang.leal.ahel.http.json.GsonManager;

public final class PostService {
    private final String url;
    private final Map<String, String> headerMap = new HashMap<>();
    private final Map<String, String> queryMap = new HashMap<>();
    private final Map<String, String> fieldMap = new HashMap<>();
    private final Map<String, RequestBody> requestBodyMap = new HashMap<>();
    private Object body;
    private final Map<String,Object> bodyParams = new HashMap<>();

    public PostService(String url) {
        this.url = url;
    }

    public <T> Observable<T> observable(Class<T> clazz) {
        Function<String, T> function = s -> GsonManager.gson().fromJson(s, clazz);
        if (body != null||bodyParams.size()>0) {
            if (body!=null){
                Gson gson = GsonManager.gson();
                bodyParams.putAll(gson.fromJson(gson.toJson(body),new TypeToken<HashMap<String, Object>>(){}.getType()));
            }
            Observable<String> stringObservable = Api.create(PostApi.class).body(url, headerMap, bodyParams, queryMap);
            return stringObservable.map(function);
        }

        if (fieldMap.size() > 0 && requestBodyMap.size() == 0) {
            Observable<String> stringObservable = Api.create(PostApi.class).field(url, headerMap, fieldMap, queryMap);
            return stringObservable.map(function);
        }

        Map<String, RequestBody> partMap = new HashMap<>();
        if (fieldMap.size() > 0) {
            for (Map.Entry<String, String> fieldEntry : fieldMap.entrySet()) {
                if (fieldEntry != null) {
                    partMap.put(fieldEntry.getKey(), RequestBody.create(MediaType.parse("text/plain"),fieldEntry.getValue()));
                }
            }
        }
        if (requestBodyMap.size() > 0) {
            for (Map.Entry<String, RequestBody> fileEntry : requestBodyMap.entrySet()) {
                partMap.put(fileEntry.getKey(), fileEntry.getValue());
            }
        }
        if (partMap.size() == 0) {
            Observable<String> stringObservable = Api.create(PostApi.class).post(url, headerMap, queryMap);
            return stringObservable.map(function);
        } else {
            Observable<String> stringObservable = Api.create(PostApi.class).part(url, headerMap, partMap, queryMap);
            return stringObservable.map(function);
        }
    }

    public PostService header(String key, String value) {
        this.headerMap.put(key, value);
        return this;
    }

    public PostService headerMap(Map<String, String> headerMap) {
        this.headerMap.putAll(headerMap);
        return this;
    }

    public PostService query(String key, String value) {
        this.queryMap.put(key, value);
        return this;
    }

    public PostService queryMap(Map<String, String> queryMap) {
        this.queryMap.putAll(queryMap);
        return this;
    }

    /**
     * Body 有且仅有一个，并且会覆盖掉fieldMap;requestBodyMap
     *
     * @param body Object
     */
    public PostService body(Object body) {
        this.body = body;
        return this;
    }

    public PostService body(String key,Object value){
        bodyParams.put(key,value);
        return this;
    }

    public PostService field(String key, String value) {
        this.fieldMap.put(key, value);
        return this;
    }

    public PostService fieldMap(Map<String, String> fieldMap) {
        this.fieldMap.putAll(fieldMap);
        return this;
    }

    public PostService image(String name, File image) {
        return file(name, image, "image/*");
    }

    public PostService video(String name, File video) {
        return file(name, video, "video/*");
    }

    public PostService audio(String name, File audio) {
        return file(name, audio, "audio/*");
    }

    public PostService file(String name, File file) {
        return file(name, file, "");
    }

    private PostService file(String name, File file, String mimeType) {
        if (!file.exists()) {
            return this;
        }
        requestBodyMap.put(name, RequestBody.create(MediaType.parse(mimeType),file));
        return this;
    }


    public interface PostApi {
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

    }
}
