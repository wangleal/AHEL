package wang.leal.ahel.http.api.service.retrofit.converter;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import wang.leal.ahel.http.api.ApiCode;
import wang.leal.ahel.http.api.exception.ApiException;
import wang.leal.ahel.http.api.service.entity.Result;
import wang.leal.ahel.http.utils.Utils;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Default Response
 * Created by wang leal on 2016/6/20.
 */
public class ApiResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    public ApiResponseConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        String json = value.string();
        Class returnRawType = Utils.getRawType(this.type);
        Result<Object> result = gson.fromJson(json, Utils.getParameterized(null,Result.class, Object.class));
        if (result.getCode()==ApiCode.CODE_SUCCESS){
            if (returnRawType==Result.class){
                return gson.fromJson(json,this.type);
            }else {
                return gson.fromJson(gson.toJson(result.getData()),this.type);
            }
        }else {
            throw new ApiException(result.getCode(), result.getMessage(),json);
        }
    }
}
