package wang.leal.ahel.http.api.observable;

import androidx.annotation.NonNull;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public final class CallEnqueueObservable extends Observable<Response> {
    private final Call originalCall;

    public CallEnqueueObservable(Call originalCall) {
        this.originalCall = originalCall;
    }

    @Override protected void subscribeActual(Observer<? super Response> observer) {
        // Since Call is a one-shot type, clone it for each new observer.
        Call call = originalCall.clone();
        CallCallback callback = new CallCallback(call, observer);
        observer.onSubscribe(callback);
        call.enqueue(callback);
    }

    private static final class CallCallback implements Disposable, Callback {
        private final Call call;
        private final Observer<? super Response> observer;
        boolean terminated = false;

        CallCallback(Call call, Observer<? super Response> observer) {
            this.call = call;
            this.observer = observer;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            if (call.isCanceled()) return;

            try {
                observer.onError(e);
            } catch (Throwable inner) {
                e.printStackTrace();
            }
        }

        @Override public void onResponse(Call call, @NonNull Response response) {
            if (call.isCanceled()) return;

            try {
                observer.onNext(response);

                if (!call.isCanceled()) {
                    terminated = true;
                    observer.onComplete();
                }
            } catch (Throwable t) {
                if (terminated) {
                    RxJavaPlugins.onError(t);
                } else if (!call.isCanceled()) {
                    try {
                        observer.onError(t);
                    } catch (Throwable inner) {
                        Exceptions.throwIfFatal(inner);
                        RxJavaPlugins.onError(new CompositeException(t, inner));
                    }
                }
            }
        }

        @Override public void dispose() {
            call.cancel();
        }

        @Override public boolean isDisposed() {
            return call.isCanceled();
        }
    }
}
