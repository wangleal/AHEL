package wang.leal.ahel.socket;

import android.content.Context;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import wang.leal.ahel.socket.process.Client;
import wang.leal.ahel.socket.process.Data;
import wang.leal.ahel.socket.process.MessageType;

public class Socket{
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
    public static void startProcess(Context context){
        Client.getInstance().startSocketProcess(context);
    }

    /**
     * stop socket process.
     * @param context context
     */
    public static void stopProcess(Context context){
        Client.getInstance().stopSocketProcess(context);
    }

    public static Socket connect(String url,int port){
        Client.getInstance().sendMessage(MessageType.CONNECT,new Data(url,port,null));
        return new Socket(url,port);
    }

    public void disconnect(){
        Client.getInstance().sendMessage(MessageType.DISCONNECT,new Data(url,port,null));
    }

    public Flowable<String> registerMessage(){
        return Client.getInstance().registerMessage(url,port);
    }
}
