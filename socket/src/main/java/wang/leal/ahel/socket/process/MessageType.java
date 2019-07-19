package wang.leal.ahel.socket.process;

public interface MessageType {

    int PING = 1;
    int PONG = 2;
    int CONNECT = 3;//client request connect socket.
    int MESSAGE = 4;//send message from client to server or send message from server to client.
    int DISCONNECT = 5;//client request disconnect socket.
    int CONNECT_SUCCESS = 6;//server notifies client that the socket connection is successful.
    int CONNECT_FAIL = 7;//server notifies client that the socket connection is failed.
    int DISCONNECT_SUCCESS = 8;//server notifies client that the socket disconnection is successful.
    int DISCONNECT_FAIL = 9;//server notifies client that the socket disconnection is failed.
}
