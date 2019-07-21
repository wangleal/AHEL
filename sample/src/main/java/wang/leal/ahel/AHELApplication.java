package wang.leal.ahel;

import android.app.Application;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.config.ApiConfig;
import wang.leal.ahel.http.api.config.Result;
import wang.leal.ahel.http.okhttp.OkHttpManager;
import wang.leal.ahel.sample.socket.ReceiveProcessor;
import wang.leal.ahel.sample.socket.RequestProcessor;
import wang.leal.ahel.socket.Socket;
import wang.leal.ahel.socket.process.ProcessListener;

public class AHELApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Api.initialize(new ApiConfig.Builder()
                .client(OkHttpManager.getApiOkHttpClient())
                .result(new Result("code","message","data",0))
                .type(ApiConfig.Type.RETROFIT)
                .baseUrl("http://test.leal.wang")
                .build());
//        Socket.startProcess(getApplicationContext(), new ProcessListener() {
//            @Override
//            public void onConnected() {
//                Socket socket = Socket.connectOrGet("192.168.1.106",9999);
//                socket.registerMessage()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<String>() {
//                            @Override
//                            public void accept(String s) throws Exception {
//                                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//                            }
//                        });
//            }
//        });
        Socket.startProcess(getApplicationContext());
    }
}
