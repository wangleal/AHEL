package wang.leal.ahel.http.api.converter;

import okhttp3.MediaType;
import okhttp3.RequestBody;

final class ScalarRequestBodyConverter<T> implements Converter<T, RequestBody> {
  static final ScalarRequestBodyConverter<Object> INSTANCE = new ScalarRequestBodyConverter<>();
  private static final MediaType MEDIA_TYPE = MediaType.get("text/plain; charset=UTF-8");

  private ScalarRequestBodyConverter() {}

  @Override
  public RequestBody convert(T value) {
    return RequestBody.create(MEDIA_TYPE,String.valueOf(value));
  }
}
