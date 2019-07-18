package wang.leal.ahel.socket;

import wang.leal.ahel.socket.netty.Netty;

public class Socket implements IConnection.OnMessageReceiveListener {
    private IConnection connection;
    private Callback callback;

    private Socket(IConnection connection){
        this.connection = connection;
        this.connection.listen(this);
    }

    public static Socket connect(String url,int port){
        IConnection connection = getConnection();
        connection.connect(url,port);
        return new Socket(connection);
    }

    public Socket callback(Callback callback){
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
