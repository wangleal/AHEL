package wang.leal.ahel;

import android.app.Application;

import wang.leal.ahel.sample.socket.Connection;

public class AHELApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Connection.init(getApplicationContext());
    }
}
