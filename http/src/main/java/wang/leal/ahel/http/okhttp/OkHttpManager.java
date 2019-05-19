package wang.leal.ahel.http.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttpManager
 * Created by wang leal on 2016/6/15.
 */
public class OkHttpManager {

    public static OkHttpClient getApiOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设定30秒超时
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30,TimeUnit.SECONDS);
        builder.writeTimeout(30,TimeUnit.SECONDS);
        builder.cookieJar(new DefaultCookieJar());
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addNetworkInterceptor(httpLoggingInterceptor);
        //构建OKHttpClient
        return builder.build();
    }

    public static OkHttpClient getUploadOkHttpClient(boolean isShowLog){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设定30秒超时
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60,TimeUnit.SECONDS);
        builder.writeTimeout(60,TimeUnit.SECONDS);
        builder.cookieJar(new DefaultCookieJar());
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        builder.addNetworkInterceptor(httpLoggingInterceptor);
        //构建OKHttpClient
        return builder.build();
    }

    public static OkHttpClient getDownloadOkHttpClient(boolean isShowLog){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设定30秒超时
        builder.connectTimeout(60*2, TimeUnit.SECONDS);
        builder.readTimeout(60*2,TimeUnit.SECONDS);
        builder.writeTimeout(60*2,TimeUnit.SECONDS);
        builder.cookieJar(new DefaultCookieJar());
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        builder.addNetworkInterceptor(httpLoggingInterceptor);
        //构建OKHttpClient
        return builder.build();
    }
}
