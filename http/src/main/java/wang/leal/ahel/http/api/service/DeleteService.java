package wang.leal.ahel.http.api.service;

import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.annotation.DELETE;
import wang.leal.ahel.http.api.annotation.HeaderMap;
import wang.leal.ahel.http.api.annotation.QueryMap;
import wang.leal.ahel.http.api.annotation.Url;
import wang.leal.ahel.http.json.GsonManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

public final class DeleteService {
    protected String url;
    protected Map<String,String> headerMap = new HashMap<>();
    protected Map<String,String> queryMap = new HashMap<>();

    public <T> Observable<T> observable(Class<T> clazz){
        Observable<String> stringObservable = Api.INSTANCE.create(DeleteApi.class)
                .delete(url,headerMap,queryMap);
        return stringObservable.map(s -> GsonManager.INSTANCE.gson().fromJson(s,clazz));
    }

    public DeleteService(String url){
        this.url = url;
    }

    public DeleteService header(String key, String value){
        this.headerMap.put(key,value);
        return this;
    }

    public DeleteService headerMap(Map<String,String> headerMap){
        this.headerMap.putAll(headerMap);
        return this;
    }

    public DeleteService query(String key, String value){
        this.queryMap.put(key,value);
        return this;
    }

    public DeleteService queryMap(Map<String,String> queryMap){
        this.queryMap.putAll(queryMap);
        return this;
    }

    public interface DeleteApi{
        @DELETE
        Observable<String> delete(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> queryMap);
    }
}
