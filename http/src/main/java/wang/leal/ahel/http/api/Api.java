package wang.leal.ahel.http.api;

import wang.leal.ahel.http.api.service.CancelService;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.api.service.PostService;

/**
 * Base Api
 * Created by wang leal on 16/5/29.
 */
public final class Api {

    public static  <T> T create(Class<T> clazz){
        return ApiHelper.createService().create(clazz);
    }

    public static GetService get(String url){
        return ApiHelper.getService(url);
    }

    public static PostService post(String url){
        return ApiHelper.postService(url);
    }

    public static CancelService cancel(){
        return ApiHelper.cancelService();
    }
}
