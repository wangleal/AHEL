package wang.leal.ahel.socket.log;

import android.util.Log;

public class Logger {
    private static final String TAG = "Socket.Logger";
    private static boolean isShow = false;

    public static void close(){
        isShow = false;
    }

    public static void open(){
        isShow = true;
    }
    public static void e(String message){
        if (isShow){
            Log.e(TAG,message);
        }
    }

}
