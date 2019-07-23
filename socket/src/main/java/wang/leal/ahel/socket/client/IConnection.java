package wang.leal.ahel.socket.client;

public interface IConnection {
    void connect(String host, int port,OnConnectionListener onConnectionListener);
    void close();
    void reconnect(String host, int port);
    void sendMessage(String message);

    interface OnConnectionListener{
        void onMessageReceive(String message);
        void onConnected();
    }
}
