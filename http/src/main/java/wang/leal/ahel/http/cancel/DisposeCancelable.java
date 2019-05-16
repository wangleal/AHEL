package wang.leal.ahel.http.cancel;

import io.reactivex.disposables.Disposable;

public class DisposeCancelable implements Cancelable {
    private Disposable disposable;
    public DisposeCancelable(Disposable disposable){
        this.disposable = disposable;
    }

    @Override
    public void cancel() {
        if (disposable!=null&&!disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
