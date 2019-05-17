package wang.leal.ahel.http.api.service;

import wang.leal.ahel.http.api.service.entity.FileEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by wang leal on 2017/10/19.
 */

public abstract class PostService {
    protected String baseUrl;//本次请求的baseUrl

    protected String url;
    protected Map<String,String> headerMap = new HashMap<>();
    protected Map<String,String> queryMap = new HashMap<>();
    protected Map<String,String> fieldMap = new HashMap<>();
    protected List<FileEntity> fileList = new ArrayList<>();
    protected Object body;

    public abstract <T> Observable<T> observable(Class<T> clazz);

    public PostService(String url){
        this.url = url;
    }

    public PostService header(String key, String value){
        this.headerMap.put(key,value);
        return this;
    }

    public PostService headerMap(Map<String,String> headerMap){
        this.headerMap.putAll(headerMap);
        return this;
    }

    public PostService query(String key, String value){
        this.queryMap.put(key,value);
        return this;
    }

    public PostService queryMap(Map<String,String> queryMap){
        this.queryMap.putAll(queryMap);
        return this;
    }

    /**
     * Body 有且仅有一个，并且会覆盖掉fieldMap;fileMap
     * @param body
     * @return
     */
    public PostService body(Object body){
        this.body = body;
        return this;
    }

    public PostService field(String key,String value){
        this.fieldMap.put(key,value);
        return this;
    }

    public PostService fieldMap(Map<String,String> fieldMap){
        this.fieldMap.putAll(fieldMap);
        return this;
    }

    public PostService file(String name,String mime,File file){
        if (file!=null){
            this.fileList.add(new FileEntity(name,mime,file));
        }
        return this;
    }
}
