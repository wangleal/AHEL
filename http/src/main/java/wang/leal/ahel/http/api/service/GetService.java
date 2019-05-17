package wang.leal.ahel.http.api.service;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by wang leal on 2017/10/19.
 */

public abstract class GetService {
    protected String baseUrl;//本次请求的baseUrl

    protected String url;
    protected Map<String,String> headerMap = new HashMap<>();
    protected Map<String,String> queryMap = new HashMap<>();

    public abstract <T> Observable<T> observable(Class<T> clazz);

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

}
