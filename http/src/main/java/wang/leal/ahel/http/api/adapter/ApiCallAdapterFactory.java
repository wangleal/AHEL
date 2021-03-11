package wang.leal.ahel.http.api.adapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Observable;

public final class ApiCallAdapterFactory extends CallAdapter.Factory {

    public static ApiCallAdapterFactory create() {
        return new ApiCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations) {
        Class<?> rawType = getRawType(returnType);

        if (rawType != Observable.class) {
            throw new IllegalStateException("Return type must be Observable.");
        }

        if (!(returnType instanceof ParameterizedType)) {
            String name = "Observable";
            throw new IllegalStateException(
                    name
                            + " return type must be parameterized"
                            + " as "
                            + name
                            + "<Foo> or "
                            + name
                            + "<? extends Foo>");
        }

        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        return new ApiCallAdapter<>(observableType);
    }
}
