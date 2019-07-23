package wang.leal.ahel.socket.client;

import wang.leal.ahel.socket.log.Logger;
import wang.leal.ahel.socket.netty.Netty;

public class SocketClient implements IConnection.OnConnectionListener {
    private IConnection connection;
    private Callback callback;
    private String url;
    private int port;

    private SocketClient(String url,int port){
        this.url = url;
        this.port = port;
    }

    public static SocketClient with(String url, int port){
        return new SocketClient(url,port);
    }

    public SocketClient connect(){
        this.connection = getConnection();
        return this;
    }

    public SocketClient callback(Callback callback){
        this.callback = callback;
        return this;
    }

    public void send(String message){
        if (connection!=null){
            connection.sendMessage(message);
        }
    }

    public void disconnect(){
        Logger.e("socket client disconnect");
        this.connection.close();
    }

    private static IConnection getConnection(){
        return new Netty();
    }

    @Override
    public void onMessageReceive(String message) {
        if (callback!=null){
            callback.onMessageReceive(this.url,this.port,message);
        }
    }

    @Override
    public void onConnected() {
        if (callback!=null){
            callback.onConnected(this.url,this.port);
        }
    }

    public interface Callback {
        void onMessageReceive(String url,int port,String message);
        void onConnected(String url,int port);
    }
}
