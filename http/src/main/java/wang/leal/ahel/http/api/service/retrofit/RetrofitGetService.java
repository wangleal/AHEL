package wang.leal.ahel.http.api.service.retrofit;

import com.google.gson.Gson;

import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.response.Origin;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.json.gson.GsonManager;

import java.util.Map;

import io.reactivex.Observable;
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
        Gson gson = GsonManager.gson();
        if (Origin.class.isAssignableFrom(clazz)){
            Observable<Object> originObservable = Api.create(GetApi.class)
                    .getOrigin(url,headerMap,queryMap);
            return originObservable.map(origin -> gson.fromJson(gson.toJson(origin),clazz));
        }else {
            Observable<String> stringObservable = Api.create(GetApi.class)
                    .get(url,headerMap,queryMap);
            return stringObservable.map(s -> gson.fromJson(s,clazz));
        }
    }

    public interface GetApi{
        @GET
        Observable<String> get(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> queryMap);

        @GET
        Observable<Object> getOrigin(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> queryMap);
    }
}
