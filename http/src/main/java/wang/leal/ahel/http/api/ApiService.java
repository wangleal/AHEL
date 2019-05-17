package wang.leal.ahel.http.api;

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import wang.leal.ahel.http.api.config.ApiConfig;
import wang.leal.ahel.http.api.config.Result;
import wang.leal.ahel.http.api.service.CancelService;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.api.service.PostService;
import wang.leal.ahel.http.api.service.ServiceFactory;
import wang.leal.ahel.http.api.service.retrofit.RetrofitServiceFactory;

public class ApiService {

    private ServiceFactory serviceFactory = new RetrofitServiceFactory();
    private static ApiConfig apiConfig = new ApiConfig.Builder().build();

    void setApiConfig(ApiConfig apiConfig){
        ApiService.apiConfig = apiConfig;
    }

    public <T> T create(Class<T> clazz){
        return serviceFactory.createService().create(clazz);
    }

    public GetService get(String url){
        return serviceFactory.getService(url);
    }

    public PostService post(String url){
        return serviceFactory.postService(url);
    }

    public CancelService cancel(){
        return serviceFactory.cancelService();
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

    public static String baseUrl(){
        if (TextUtils.isEmpty(apiConfig.baseUrl())){
            return "http://api.leal.wang/";
        }
        return apiConfig.baseUrl();
    }
}
