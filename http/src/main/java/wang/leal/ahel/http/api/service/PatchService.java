package wang.leal.ahel.http.api.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.annotation.Body;
import wang.leal.ahel.http.api.annotation.HeaderMap;
import wang.leal.ahel.http.api.annotation.PATCH;
import wang.leal.ahel.http.api.annotation.QueryMap;
import wang.leal.ahel.http.api.annotation.Url;
import wang.leal.ahel.http.json.GsonManager;

public final class PatchService {
    private final String url;
    private final Map<String,String> headerMap = new HashMap<>();
    private final Map<String,String> queryMap = new HashMap<>();
    private Object body;
    private final Map<String,Object> bodyParams = new HashMap<>();

    public <T> Observable<T> observable(Class<T> clazz){
        Function<String,T> function = s -> GsonManager.gson().fromJson(s,clazz);
        if (body != null||bodyParams.size()>0) {
            if (body!=null){
                Gson gson = GsonManager.gson();
                bodyParams.putAll(gson.fromJson(gson.toJson(body),new TypeToken<HashMap<String, Object>>(){}.getType()));
            }
            Observable<String> stringObservable = Api.create(PatchApi.class).body(url, headerMap, bodyParams, queryMap);
            return stringObservable.map(function);
        }
        Observable<String> stringObservable = Api.create(PatchApi.class)
                .patch(url,headerMap,queryMap);
        return stringObservable.map(function);
    }

    public PatchService(String url){
        this.url = url;
    }

    public PatchService header(String key, String value){
        this.headerMap.put(key,value);
        return this;
    }

    public PatchService headerMap(Map<String,String> headerMap){
        this.headerMap.putAll(headerMap);
        return this;
    }

    public PatchService query(String key, String value){
        this.queryMap.put(key,value);
        return this;
    }

    public PatchService queryMap(Map<String,String> queryMap){
        this.queryMap.putAll(queryMap);
        return this;
    }

    public PatchService body(Object body){
        this.body = body;
        return this;
    }

    public PatchService body(String key,Object value){
        bodyParams.put(key,value);
        return this;
    }

    public interface PatchApi{
        @PATCH
        Observable<String> patch(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> queryMap);

        @PATCH
        Observable<String> body(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object body, @QueryMap Map<String, String> queryMap);
    }
}
