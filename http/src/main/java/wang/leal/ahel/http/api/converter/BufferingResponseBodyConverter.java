package wang.leal.ahel.http.api.converter;

import wang.leal.ahel.http.utils.Utils;

import java.io.IOException;

import okhttp3.ResponseBody;

final class BufferingResponseBodyConverter
        implements Converter<ResponseBody, ResponseBody> {
    static final BufferingResponseBodyConverter INSTANCE = new BufferingResponseBodyConverter();

    @Override
    public ResponseBody convert(ResponseBody value) throws IOException {
        try {
            // Buffer the entire body to avoid future I/O.
            return Utils.buffer(value);
        } finally {
            value.close();
        }
    }
}
