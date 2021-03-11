package wang.leal.ahel.http.api.create;

import androidx.annotation.Nullable;

import wang.leal.ahel.http.api.adapter.ApiCallAdapterFactory;
import wang.leal.ahel.http.api.adapter.CallAdapter;
import wang.leal.ahel.http.api.converter.ApiConverterFactory;
import wang.leal.ahel.http.api.converter.Converter;
import wang.leal.ahel.http.utils.Utils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public abstract class ServiceMethod<T> {
  static final Converter.Factory converterFactory = ApiConverterFactory.create();
  static final CallAdapter.Factory callAdapterFactory = ApiCallAdapterFactory.create();
  public static <T> ServiceMethod<T> parseAnnotations(Method method) {
    RequestFactory requestFactory = RequestFactory.parseAnnotations(method);

    Type returnType = method.getGenericReturnType();
    if (Utils.hasUnresolvableType(returnType)) {
      throw Utils.methodError(
          method,
          "Method return type must not include a type variable or wildcard: %s",
          returnType);
    }
    if (returnType == void.class) {
      throw Utils.methodError(method, "Service methods cannot return void.");
    }

    return HttpServiceMethod.parseAnnotations(method, requestFactory);
  }

  public abstract @Nullable T invoke(Object[] args);
}
