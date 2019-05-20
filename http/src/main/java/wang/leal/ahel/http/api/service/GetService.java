package wang.leal.ahel.http.api.service;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by wang leal on 2017/10/19.
 */

public abstract class GetService {

    protected String url;
    protected Map<String,String> headerMap = new HashMap<>();
    protected Map<String,String> queryMap = new HashMap<>();

    /**
     * 获取Observable<T>
     * 两种特殊情况：
     *      1，当参数T为Object的时候，onSuccess返回的是response.body的object对象
     *      2，当参数T为Origin的子类的时候，onSuccess返回的是response.body的T对象
     * @param clazz Class
     * @param <T> T
     * @return  Observable
     */
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
