package wang.leal.ahel.http.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import wang.leal.ahel.http.utils.Utils;

/**
 * OkHttpManager
 * Created by wang leal on 2016/6/15.
 */
public class OkHttpManager {

    private static OkHttpClient apiOkHttpClient;

    private static OkHttpClient downloadHttpClient;

    private static OkHttpClient uploadHttpClient;

    public static OkHttpClient getApiOkHttpClient(){
        if (apiOkHttpClient ==null){
            synchronized (OkHttpManager.class){
                if (apiOkHttpClient==null){
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    //设定30秒超时
                    builder.connectTimeout(30, TimeUnit.SECONDS);
                    builder.readTimeout(30,TimeUnit.SECONDS);
                    builder.writeTimeout(30,TimeUnit.SECONDS);
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(Utils.isDebug()?HttpLoggingInterceptor.Level.BODY: HttpLoggingInterceptor.Level.NONE);
                    builder.addNetworkInterceptor(httpLoggingInterceptor);
                    //构建OKHttpClient
                    apiOkHttpClient = builder.build();
                }
            }
        }
        return apiOkHttpClient;
    }

    public static OkHttpClient getUploadOkHttpClient(){
        if (uploadHttpClient==null){
            synchronized (OkHttpManager.class){
                if (uploadHttpClient==null){
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    //设定30秒超时
                    builder.connectTimeout(30, TimeUnit.SECONDS);
                    builder.readTimeout(30,TimeUnit.SECONDS);
                    builder.writeTimeout(30,TimeUnit.SECONDS);
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(Utils.isDebug()?HttpLoggingInterceptor.Level.HEADERS: HttpLoggingInterceptor.Level.NONE);
                    builder.addNetworkInterceptor(httpLoggingInterceptor);
                    //构建OKHttpClient
                    uploadHttpClient = builder.build();
                }
            }

        }
        return uploadHttpClient;
    }

    public static OkHttpClient getDownloadOkHttpClient(){
        if (downloadHttpClient==null){
            synchronized (OkHttpManager.class){
                if (downloadHttpClient==null){
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    //设定30秒超时
                    builder.connectTimeout(30, TimeUnit.SECONDS);
                    builder.readTimeout(30,TimeUnit.SECONDS);
                    builder.writeTimeout(30,TimeUnit.SECONDS);
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(Utils.isDebug()?HttpLoggingInterceptor.Level.HEADERS: HttpLoggingInterceptor.Level.NONE);
                    builder.addNetworkInterceptor(httpLoggingInterceptor);
                    //构建OKHttpClient
                    downloadHttpClient = builder.build();
                }
            }
        }
        return downloadHttpClient;
    }
}
