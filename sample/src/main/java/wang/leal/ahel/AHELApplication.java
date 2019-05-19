package wang.leal.ahel;

import android.app.Application;

import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.config.ApiConfig;
import wang.leal.ahel.http.api.config.Result;
import wang.leal.ahel.http.okhttp.OkHttpManager;

public class AHELApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Api.initialize(new ApiConfig.Builder().client(OkHttpManager.getApiOkHttpClient()).result(new Result("code","message","data",0)).type(ApiConfig.Type.RETROFIT).baseUrl("http://test.leal.wang").build());
    }
}
