package wang.leal.ahel.http.api.service.retrofit;

import android.text.TextUtils;

import wang.leal.ahel.http.api.ApiHelper;
import wang.leal.ahel.http.api.service.CreateService;
import wang.leal.ahel.http.api.service.retrofit.converter.ApiConverterFactory;
import wang.leal.ahel.http.okhttp.OkHttpManager;

import java.lang.reflect.Field;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by wang leal on 2017/8/29.
 */

public class RetrofitCreateService extends CreateService {
    private static final String API_SERVER = "https://api.github.com";

    private static Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(API_SERVER)
            .addConverterFactory(ApiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(ApiHelper.client())
            .build();
    @Override
    public <T> T create(Class<T> service) {
        String baseUrl = API_SERVER;
        try {
            Field field = service.getDeclaredField("BASE_URL");
            field.setAccessible(true);
            baseUrl = (String) field.get(service);
        } catch (Exception ignored) {
        }
        if (TextUtils.isEmpty(baseUrl)){
            baseUrl = API_SERVER;
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
