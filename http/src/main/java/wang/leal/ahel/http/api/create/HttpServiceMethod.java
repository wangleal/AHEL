package wang.leal.ahel.http.api.create;

import androidx.annotation.Nullable;

import wang.leal.ahel.http.api.adapter.CallAdapter;
import wang.leal.ahel.http.api.converter.Converter;
import wang.leal.ahel.http.okhttp.OkHttpManager;
import wang.leal.ahel.http.utils.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/** Adapts an invocation of an interface method into an HTTP call. */
abstract class HttpServiceMethod<ResponseT, ReturnT> extends ServiceMethod<ReturnT> {
  /**
   * Inspects the annotations on an interface method to construct a reusable service method that
   * speaks HTTP. This requires potentially-expensive reflection so it is best to build each service
   * method only once and reuse it.
   */
  static <ResponseT, ReturnT> HttpServiceMethod<ResponseT, ReturnT> parseAnnotations(
          Method method, RequestFactory requestFactory) {

    Annotation[] annotations = method.getAnnotations();
    Type adapterType = method.getGenericReturnType();

    CallAdapter<ResponseT, ReturnT> callAdapter =
        createCallAdapter(method, adapterType, annotations);
    Type responseType = callAdapter.responseType();
    if (responseType == okhttp3.Response.class) {
      throw Utils.methodError(
          method,
          "'"
              + Utils.getRawType(responseType).getName()
              + "' is not a valid response body type. Did you mean ResponseBody?");
    }
    if (responseType == Response.class) {
      throw Utils.methodError(method, "Response must include generic type (e.g., Response<String>)");
    }

    if (requestFactory.httpMethod.equals("HEAD") && !Void.class.equals(responseType)) {
      throw Utils.methodError(method, "HEAD method must use Void as response type.");
    }

    Converter<ResponseBody, ResponseT> responseConverter =
        createResponseConverter(method, responseType);

    okhttp3.Call.Factory callFactory = OkHttpManager.INSTANCE.getApiOkHttpClient();
    return new CallAdapted<>(requestFactory, callFactory, responseConverter, callAdapter);
  }

  @SuppressWarnings("unchecked")
  private static <ResponseT, ReturnT> CallAdapter<ResponseT, ReturnT> createCallAdapter(
          Method method, Type returnType, Annotation[] annotations) {
    try {
      return (CallAdapter<ResponseT, ReturnT>) callAdapterFactory.get(returnType, annotations);
    } catch (RuntimeException e) { // Wide exception range because factories are user code.
      throw Utils.methodError(method, e, "Unable to create call adapter for %s", returnType);
    }
  }

  @SuppressWarnings("unchecked")
  private static <ResponseT> Converter<ResponseBody, ResponseT> createResponseConverter(
          Method method, Type responseType) {
    Annotation[] annotations = method.getAnnotations();
    try {
      return (Converter<ResponseBody, ResponseT>) converterFactory.responseBodyConverter(responseType, annotations);
    } catch (RuntimeException e) { // Wide exception range because factories are user code.
      throw Utils.methodError(method, e, "Unable to create converter for %s", responseType);
    }
  }

  private final RequestFactory requestFactory;
  private final okhttp3.Call.Factory callFactory;
  private final Converter<ResponseBody, ResponseT> responseConverter;

  HttpServiceMethod(
      RequestFactory requestFactory,
      okhttp3.Call.Factory callFactory,
      Converter<ResponseBody, ResponseT> responseConverter) {
    this.requestFactory = requestFactory;
    this.callFactory = callFactory;
    this.responseConverter = responseConverter;
  }

  @Override
  public final @Nullable ReturnT invoke(Object[] args) {
    Call<ResponseT> call = new OkHttpCall<>(requestFactory, args, callFactory, responseConverter);
    return adapt(call, args);
  }

  protected abstract @Nullable ReturnT adapt(Call<ResponseT> call, Object[] args);

  static final class CallAdapted<ResponseT, ReturnT> extends HttpServiceMethod<ResponseT, ReturnT> {
    private final CallAdapter<ResponseT, ReturnT> callAdapter;

    CallAdapted(
        RequestFactory requestFactory,
        okhttp3.Call.Factory callFactory,
        Converter<ResponseBody, ResponseT> responseConverter,
        CallAdapter<ResponseT, ReturnT> callAdapter) {
      super(requestFactory, callFactory, responseConverter);
      this.callAdapter = callAdapter;
    }

    @Override
    protected ReturnT adapt(Call<ResponseT> call, Object[] args) {
      return callAdapter.adapt(call);
    }
  }
}
