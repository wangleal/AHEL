package wang.leal.ahel.http.api.converter;

import okhttp3.ResponseBody;

final class StreamingResponseBodyConverter implements Converter<ResponseBody, ResponseBody> {
    static final StreamingResponseBodyConverter INSTANCE = new StreamingResponseBodyConverter();

    @Override
    public ResponseBody convert(ResponseBody value) {
        return value;
    }
}
