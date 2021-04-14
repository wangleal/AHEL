package wang.leal.ahel;

import android.app.Application;
import android.util.Log;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import wang.leal.ahel.lifecycle.Lifecycle;
import wang.leal.ahel.lifecycle.ProcessLifecycle;
import wang.leal.ahel.sample.socket.Connection;

public class AHELApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Connection.init(getApplicationContext());
        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);
        Lifecycle.INSTANCE.initialize(this);

        ProcessLifecycle.INSTANCE.observable().subscribeOn(Schedulers.computation())
                .subscribe(event -> Log.e("Sample","event:"+event.name()));
    }
}
