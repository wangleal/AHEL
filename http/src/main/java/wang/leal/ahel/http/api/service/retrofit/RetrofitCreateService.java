package wang.leal.ahel.http.api.service.retrofit;

import android.text.TextUtils;

import wang.leal.ahel.http.api.ApiService;
import wang.leal.ahel.http.api.service.CreateService;
import wang.leal.ahel.http.api.service.retrofit.converter.ApiConverterFactory;

import java.lang.reflect.Field;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by wang leal on 2017/8/29.
 */

public class RetrofitCreateService extends CreateService {
    private static Retrofit retrofit;

    public RetrofitCreateService(){
        if (retrofit==null){
            synchronized (RetrofitCreateService.class){
                if (retrofit==null){
                    retrofit =  new Retrofit.Builder()
                            .baseUrl(ApiService.baseUrl())
                            .addConverterFactory(ApiConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(ApiService.client())
                            .build();
                }
            }
        }
    }

    @Override
    public <T> T create(Class<T> service) {
        String baseUrl = ApiService.baseUrl();
        try {
            Field field = service.getDeclaredField("BASE_URL");
            field.setAccessible(true);
            baseUrl = (String) field.get(service);
        } catch (Exception ignored) {
        }
        if (TextUtils.isEmpty(baseUrl)){
            baseUrl = ApiService.baseUrl();
        }
        HttpUrl httpUrl = HttpUrl.parse(baseUrl);
        try {
            Field field = retrofit.getClass().getDeclaredField("baseUrl");
            field.setAccessible(true);
            field.set(retrofit,httpUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return retrofit.create(service);
    }
}
