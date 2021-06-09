package wang.leal.ahel.http.api.adapter;

import wang.leal.ahel.http.api.create.Call;
import wang.leal.ahel.http.api.create.Response;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import wang.leal.ahel.http.api.scheduler.HttpScheduler;

final class ApiCallAdapter<R> implements CallAdapter<R, Object> {
    private final Type responseType;

    ApiCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<R> call) {
        Observable<Response<R>> responseObservable = new ApiCallExecuteObservable<>(call);
        Observable<?> observable = new ApiObservable<>(responseObservable);
        observable = observable.subscribeOn(HttpScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread());
        return RxJavaPlugins.onAssembly(observable);
    }
}