package wang.leal.ahel.http.api.service.retrofit;

import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.json.GsonManager;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by wang leal on 2017/8/29.
 */

public class RetrofitGetService extends GetService {

    RetrofitGetService(String url) {
        super(url);
    }

    @Override
    public <T> Observable<T> observable(final Class<T> clazz) {
        Observable<String> stringObservable = Api.create(GetApi.class)
                .get(url,headerMap,queryMap);
        return stringObservable.map(new Function<String, T>() {
            @Override
            public T apply(String s) throws Exception {
                return GsonManager.gson().fromJson(s,clazz);
            }
        });
    }

    public interface GetApi{
        @GET
        Observable<String> get(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> queryMap);
    }
}
