package wang.leal.ahel.http.api.converter;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

final class ApiResponseBodyConverter <T> implements Converter<ResponseBody, T> {
    private final Type type;
    ApiResponseBodyConverter(Type type){
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        return ResponseHelper.convert(value,this.type);
    }
}
