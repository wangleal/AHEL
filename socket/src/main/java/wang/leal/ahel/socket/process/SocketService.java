package wang.leal.ahel.socket.process;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import wang.leal.ahel.socket.log.Logger;

public class SocketService extends Service {

    private Server messengerServer = new Server();

    @Override
    public final void onCreate() {
        super.onCreate();
        messengerServer.create();
    }

    @Nullable
    @Override
    public final IBinder onBind(Intent intent) {
        return messengerServer.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.e("socket service destroy");
        messengerServer.destroy();
    }
}
