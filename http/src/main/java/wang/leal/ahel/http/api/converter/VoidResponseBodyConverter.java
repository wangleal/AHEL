package wang.leal.ahel.http.api.converter;

import okhttp3.ResponseBody;

final class VoidResponseBodyConverter implements Converter<ResponseBody, Void> {
    static final VoidResponseBodyConverter INSTANCE = new VoidResponseBodyConverter();

    @Override
    public Void convert(ResponseBody value) {
        value.close();
        return null;
    }
}
