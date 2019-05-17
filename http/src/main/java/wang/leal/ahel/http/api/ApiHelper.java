package wang.leal.ahel.http.api;

import okhttp3.OkHttpClient;
import wang.leal.ahel.http.api.config.ApiConfig;
import wang.leal.ahel.http.api.config.Result;
import wang.leal.ahel.http.api.service.CancelService;
import wang.leal.ahel.http.api.service.CreateService;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.api.service.PostService;
import wang.leal.ahel.http.api.service.ServiceFactory;
import wang.leal.ahel.http.api.service.okhttp.OkHttpServiceFactory;
import wang.leal.ahel.http.api.service.retrofit.RetrofitServiceFactory;

/**
 * Created by wang leal on 2017/12/7.
 */

public final class ApiHelper {
    private static ServiceFactory serviceFactory = new RetrofitServiceFactory();
    private static ApiConfig apiConfig = new ApiConfig.Builder().build();

    static void setApiConfig(ApiConfig apiConfig){
        ApiHelper.apiConfig = apiConfig;
        if (apiConfig.type()== ApiConfig.Type.OKHTTP){
            serviceFactory = new OkHttpServiceFactory();
        }else {
            serviceFactory = new RetrofitServiceFactory();
        }
    }

    public static OkHttpClient client(){
        return apiConfig.client();
    }

    public static int successCode(){
        return apiConfig.result().successCode();
    }

    public static Result result(){
        return apiConfig.result();
    }

    static CreateService createService(){
        return serviceFactory.createService();
    }

    static GetService getService(String url){
        return serviceFactory.getService(url);
    }

    static PostService postService(String url){
        return serviceFactory.postService(url);
    }

    static CancelService cancelService(){
        return serviceFactory.cancelService();
    }
}
