package wang.leal.ahel.http.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttpManager
 * Created by wang leal on 2016/6/15.
 */
public class OkHttpManager {

    public static OkHttpClient getApiOkHttpClient(boolean isShowLog){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设定30秒超时
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30,TimeUnit.SECONDS);
        builder.writeTimeout(30,TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(isShowLog?HttpLoggingInterceptor.Level.BODY: HttpLoggingInterceptor.Level.NONE);
        builder.addNetworkInterceptor(httpLoggingInterceptor);
        //构建OKHttpClient
        return builder.build();
    }

    public static OkHttpClient getUploadOkHttpClient(boolean isShowLog){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设定30秒超时
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30,TimeUnit.SECONDS);
        builder.writeTimeout(30,TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(isShowLog?HttpLoggingInterceptor.Level.HEADERS: HttpLoggingInterceptor.Level.NONE);
        builder.addNetworkInterceptor(httpLoggingInterceptor);
        //构建OKHttpClient
        return builder.build();
    }

    public static OkHttpClient getDownloadOkHttpClient(boolean isShowLog){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设定30秒超时
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30,TimeUnit.SECONDS);
        builder.writeTimeout(30,TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(isShowLog?HttpLoggingInterceptor.Level.HEADERS: HttpLoggingInterceptor.Level.NONE);
        builder.addNetworkInterceptor(httpLoggingInterceptor);
        //构建OKHttpClient
        return builder.build();
    }
}
