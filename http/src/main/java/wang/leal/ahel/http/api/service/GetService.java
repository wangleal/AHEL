package wang.leal.ahel.http.api.service;

import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.annotation.GET;
import wang.leal.ahel.http.api.annotation.HeaderMap;
import wang.leal.ahel.http.api.annotation.QueryMap;
import wang.leal.ahel.http.api.annotation.Timeout;
import wang.leal.ahel.http.api.annotation.Url;
import wang.leal.ahel.http.json.GsonManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

public final class GetService {
    private final String url;
    private final Map<String,String> headerMap = new HashMap<>();
    private final Map<String,String> queryMap = new HashMap<>();
    private int timeout = -1;

    public <T> Observable<T> observable(Class<T> clazz){
        Observable<String> stringObservable = Api.create(GetApi.class)
                .get(timeout,url,headerMap,queryMap);
        return stringObservable.map(s -> GsonManager.gson().fromJson(s,clazz));
    }

    public GetService(String url){
        this.url = url;
    }

    public GetService header(String key, String value){
        this.headerMap.put(key,value);
        return this;
    }

    public GetService headerMap(Map<String,String> headerMap){
        this.headerMap.putAll(headerMap);
        return this;
    }

    public GetService query(String key, String value){
        this.queryMap.put(key,value);
        return this;
    }

    public GetService queryMap(Map<String,String> queryMap){
        this.queryMap.putAll(queryMap);
        return this;
    }

    public GetService timeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    public interface GetApi{
        @GET
        Observable<String> get(@Timeout int timeout,@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> queryMap);
    }
}
