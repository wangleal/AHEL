package wang.leal.ahel.http.api;

import wang.leal.ahel.http.api.config.ApiConfig;
import wang.leal.ahel.http.api.service.CancelService;
import wang.leal.ahel.http.api.service.CreateService;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.api.service.PostService;

/**
 * Api
 * Created by wang leal on 16/5/29.
 */
public final class Api {
    /**
     * 此方法可以不调用，有默认实现
     * 如果需要自定义配置，必须在第一次网络请求之前调用，否则配置不生效，网络请求仍然会使用默认的配置。
     * @param config    自定义配置
     */
    public static void initialize(ApiConfig config){
        ApiHelper.setApiConfig(config);
    }

    public static CreateService baseUrl(String baseUrl){
        return ApiHelper.createService().baseUrl(baseUrl);
    }

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
