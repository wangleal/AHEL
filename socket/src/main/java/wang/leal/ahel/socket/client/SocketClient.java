package wang.leal.ahel.socket.client;

import wang.leal.ahel.socket.log.Logger;
import wang.leal.ahel.socket.netty.Netty;

public class SocketClient implements IConnection.OnMessageReceiveListener {
    private IConnection connection;
    private Callback callback;
    private String url;
    private int port;

    private SocketClient(String url,int port,IConnection connection){
        this.url = url;
        this.port = port;
        this.connection = connection;
        this.connection.listen(this);
    }

    public static SocketClient connect(String url, int port){
        IConnection connection = getConnection();
        connection.connect(url,port);
        return new SocketClient(url,port,connection);
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

    public interface Callback {
        void onMessageReceive(String url,int port,String message);
    }
}
