package wang.leal.ahel.socket.client;

import wang.leal.ahel.socket.netty.Netty;

public class SocketClient implements IConnection.OnMessageReceiveListener {
    private IConnection connection;
    private Callback callback;

    private SocketClient(IConnection connection){
        this.connection = connection;
        this.connection.listen(this);
    }

    public static SocketClient connect(String url, int port){
        IConnection connection = getConnection();
        connection.connect(url,port);
        return new SocketClient(connection);
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

    private static IConnection getConnection(){
        return new Netty();
    }

    @Override
    public void onMessageReceive(String message) {
        if (callback!=null){
            callback.onMessageReceive(message);
        }
    }

    public interface Callback {
        void onMessageReceive(String message);
    }
}
