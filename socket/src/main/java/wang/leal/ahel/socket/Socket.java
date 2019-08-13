package wang.leal.ahel.socket;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import wang.leal.ahel.socket.log.Logger;
import wang.leal.ahel.socket.process.Client;
import wang.leal.ahel.socket.process.Data;
import wang.leal.ahel.socket.process.ProcessListener;
import wang.leal.ahel.socket.processor.LocalProcessor;
import wang.leal.ahel.socket.processor.RemoteProcessor;

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
     * start socket process.init once
     * @param context context
     */
    public static void startProcess(Context context){
        Client.getInstance().startSocketProcess(context,null);
    }

    /**
     * stop socket process.
     * @param context context
     */
    public static void stopProcess(Context context){
        Client.getInstance().stopSocketProcess(context);
    }

    public static Socket connectOrGet(String url,int port){
        return connectOrGet(url,port,null,null);
    }

    public static Socket connectOrGet(String url, int port, SocketStatusListener socketStatusListener){
        return connectOrGet(url,port,socketStatusListener,null,null);
    }

    public static Socket connectOrGet(String url, int port, LocalProcessor requestProcessor, RemoteProcessor receiveProcessor){
        return connectOrGet(url,port,null,requestProcessor,receiveProcessor);
    }

    public static Socket connectOrGet(String url, int port, SocketStatusListener socketStatusListener, LocalProcessor requestProcessor, RemoteProcessor receiveProcessor){
        Logger.e("connect or get");
        String key = url+":"+port;
        Socket socket = appSockets.get(key);
        if (socket!=null){
            Logger.e("socket is not null");
            return socket;
        }else {
            Client.getInstance().connect(url,port,socketStatusListener,requestProcessor,receiveProcessor);
            socket = new Socket(url,port);
            Logger.e("new socket");
            appSockets.put(key,socket);
            return socket;
        }
    }

    public void disconnect(){
        Logger.e("socket disconnect");
        appSockets.remove(url+":"+port);
        Client.getInstance().disconnect(url,port);
    }

    public Observable<String> registerMessage(){
        return Client.getInstance().registerMessage(url,port);
    }

    public void sendMessage(String message){
        Client.getInstance().sendMessage(new Data(url,port,message));
    }

    public static void showLog(boolean isShow){
        if (isShow){
            Logger.open();
        }else {
            Logger.close();
        }
    }
}
