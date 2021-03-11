package wang.leal.ahel.http.api.converter;

import okhttp3.RequestBody;

final class RequestBodyConverter implements Converter<RequestBody, RequestBody> {
    static final RequestBodyConverter INSTANCE = new RequestBodyConverter();

    @Override
    public RequestBody convert(RequestBody value) {
        return value;
    }
}
