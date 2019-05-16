package wang.leal.ahel.http.api.observer;

import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.exception.ApiException;
import wang.leal.ahel.http.cancel.DisposeCancelable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ApiObserver<T> implements Observer<T> {
    private Object tag;

    public ApiObserver(){
        this.tag = "ApiObserver";
    }

    public ApiObserver(Object tag){
        this.tag = tag;
    }

    @Override
    public final void onSubscribe(Disposable d) {
        Api.cancel().add(tag,new DisposeCancelable(d));
    }

    @Override
    public final void onNext(T data) {
        try {
            onSuccess(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public final void onError(Throwable e) {
        try {
            if (e instanceof ApiException){
                ApiException apiError = (ApiException) e;
                onApiError(apiError.getCode(),apiError.getMessage(),apiError.getBody());
            }else {
                onFailure(e);
            }
        }catch (Exception failureError){
            failureError.printStackTrace();
        }finally {
            try {
                onFinal();
            }catch (Exception finalError){
                finalError.printStackTrace();
            }
        }
    }

    @Override
    public final void onComplete() {
        onFinal();
    }

    //Http响应成功并且服务端code返回成功
    protected abstract void onSuccess(T data);

    //Http响应成功但是服务端code报告异常
    protected abstract void onApiError(int errNo,String errMsg,String data);

    //Http或者解析等等返回的各种错误
    protected abstract void onFailure(Throwable e);

    //无论成功或者失败，最终都会进入这里
    protected void onFinal(){

    }
}
