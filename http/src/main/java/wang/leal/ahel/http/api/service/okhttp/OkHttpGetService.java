package wang.leal.ahel.http.api.service.okhttp;

import wang.leal.ahel.http.api.ApiHelper;
import wang.leal.ahel.http.api.observable.CallExecuteObservable;
import wang.leal.ahel.http.api.observable.ExceptionObservable;
import wang.leal.ahel.http.api.response.ResponseHelper;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.exception.HttpException;
import wang.leal.ahel.http.okhttp.OkHttpManager;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpGetService extends GetService {

    OkHttpGetService(String url) {
        super(url);
    }

    @Override
    public <T> Observable<T> observable(final Class<T> clazz) {
        Request.Builder builder = new Request.Builder();
        if (headerMap!=null&&headerMap.size()>0){
            for (Map.Entry<String,String> entry:headerMap.entrySet()){
                if (entry!=null){
                    builder.addHeader(entry.getKey(),entry.getValue());
                }
            }
        }

        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl!=null){
            if (queryMap!=null&&queryMap.size()>0){
                HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
                for (Map.Entry<String,String> entry:queryMap.entrySet()){
                    if (entry!=null){
                        httpUrlBuilder.addQueryParameter(entry.getKey(),entry.getValue());
                    }
                }
                httpUrl = httpUrlBuilder.build();
            }
            builder.url(httpUrl);
        }else {
            return ExceptionObservable.nullable("Url is illegal.");
        }

        Request request = builder.get().build();
        Call call = ApiHelper.client().newCall(request);
        Observable<Response> responseObservable = new CallExecuteObservable(call);
        return  responseObservable.map(response -> {
            if (response!=null){
                if (response.isSuccessful()){
                    String json = "";
                    if (response.body()!=null){
                        json = response.body().string();
                    }
                    return ResponseHelper.dealResult(json,clazz);
                }else {
                    String body = "";
                    if (response.body()!=null){
                        body = response.body().string();
                    }
                    throw new HttpException(response.code(),response.message(),body);
                }
            }
            return null;
        });
    }

}
