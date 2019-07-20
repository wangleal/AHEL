package wang.leal.ahel.socket;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import wang.leal.ahel.socket.process.Client;
import wang.leal.ahel.socket.process.Data;
import wang.leal.ahel.socket.process.MessageType;
import wang.leal.ahel.socket.process.ProcessListener;

public class Socket{
    private static Map<String, Socket> appSockets = new HashMap<>();
    private String url;
    private int port;
    private Socket(String url,int port){
        this.url = url;
        this.port = port;
    }

    /**
     * start socket process.init once
     * @param context context
     */
    public static void startProcess(Context context, ProcessListener listener){
        Client.getInstance().startSocketProcess(context,listener);
    }

    /**
     * stop socket process.
     * @param context context
     */
    public static void stopProcess(Context context){
        Client.getInstance().stopSocketProcess(context);
    }

    public static Socket connectOrGet(String url,int port){
        String key = url+":"+port;
        Socket socket = appSockets.get(key);
        if (socket!=null){
            return socket;
        }else {
            Client.getInstance().sendMessage(MessageType.CONNECT,new Data(url,port,null));
            socket = new Socket(url,port);
            appSockets.put(key,socket);
            return socket;
        }

    }

    public void disconnect(){
        appSockets.remove(url+":"+port);
        Client.getInstance().sendMessage(MessageType.DISCONNECT,new Data(url,port,null));
    }

    public Observable<String> registerMessage(){
        return Client.getInstance().registerMessage(url,port);
    }

    public void sendMessage(String message){
        Client.getInstance().sendMessage(new Data(url,port,message));
    }
}
