package wang.leal.ahel.http.api.service.retrofit.converter;

import androidx.annotation.NonNull;

import wang.leal.ahel.http.api.response.ResponseHelper;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Default Response
 * Created by wang leal on 2016/6/20.
 */
public class ApiResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Type type;

    public ApiResponseConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        String json = value.string();
        return ResponseHelper.dealResult(json,type);
    }
}
