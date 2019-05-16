package wang.leal.ahel.http.api.observable;

import io.reactivex.Observable;

public class ExceptionObservable{

    public static Observable nullable(final String message){
        return Observable.create(emitter->emitter.onError(new NullPointerException(message)));
    }

    public static Observable exception(final String message){
        return Observable.create(emitter -> emitter.onError(new Exception(message)));
    }

}
