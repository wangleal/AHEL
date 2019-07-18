package wang.leal.ahel.socket;

public interface IConnection {
    void connect(String host, int port);
    void listen(OnMessageReceiveListener onMessageReceiveListener);
    void close();
    void reconnect(String host, int port);
    void sendMessage(String message);

    interface OnMessageReceiveListener{
        void onMessageReceive(String message);
    }
}
