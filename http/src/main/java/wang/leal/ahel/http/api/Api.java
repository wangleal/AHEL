package wang.leal.ahel.http.api;

import wang.leal.ahel.http.api.config.ApiConfig;
import wang.leal.ahel.http.api.service.CancelService;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.api.service.PostService;

/**
 * Api
 * Created by wang leal on 16/5/29.
 */
public final class Api {

    private static ApiService apiService = new ApiService();

    /**
     * 此方法可以不调用，有默认实现
     * 如果需要自定义配置，必须在第一次网络请求之前调用，否则配置不生效，网络请求仍然会使用默认的配置。
     * @param config    自定义配置
     */
    public static void initialize(ApiConfig config){
        apiService.setApiConfig(config);
    }

    public static  <T> T create(Class<T> clazz){
        return apiService.create(clazz);
    }

    public static GetService get(String url){
        return apiService.get(url);
    }

    public static PostService post(String url){
        return apiService.post(url);
    }

    public static CancelService cancel(){
        return apiService.cancel();
    }
}
