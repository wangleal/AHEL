package wang.leal.ahel.socket.process;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.HashMap;
import java.util.Map;

import wang.leal.ahel.socket.client.SocketClient;
import wang.leal.ahel.socket.log.Logger;

class Server implements SocketClient.Callback{
    private HandlerThread handlerThread;
    private Handler handler;
    private Messenger serverMessenger;
    private Messenger clientMessenger;
    private IBinder binder;
    private Map<String, SocketClient> socketClients = new HashMap<>();

    void create() {
        handlerThread = new HandlerThread("Server");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Logger.e("server receive");
                Bundle bundle = msg.getData();
                bundle.setClassLoader(getClass().getClassLoader());
                Data data = bundle.getParcelable(MessageKey.KEY_DATA);
                switch (msg.what) {
                    case MessageType.PING:
                        Logger.e("server receive ping");
                        clientMessenger = msg.replyTo;
                        Logger.e("server send pong");
                        Server.this.sendMessage(MessageType.PONG,null);
                        break;
                    case MessageType.CONNECT:
                        Logger.e("server receive connect");
                        connectOrGet(data.url,data.port);
                        break;
                    case MessageType.DISCONNECT:
                        Logger.e("server receive disconnect");
                        disconnect(data.url,data.port);
                        break;
                    case MessageType.MESSAGE:
                        Logger.e("server receive message:"+data.message);
                        SocketClient socketClient = connectOrGet(data.url,data.port);
                        socketClient.send(data.message);
                        break;
                }
            }
        };
        serverMessenger = new Messenger(handler);
        binder = serverMessenger.getBinder();
    }

    private SocketClient connectOrGet(String url,int port){
        SocketClient socketClient = getSocket(url,port);
        if (socketClient!=null){
            return socketClient;
        }else {
            socketClient = SocketClient.with(url,port).callback(this).connect();
            String key = url+":"+port;
            socketClients.put(key,socketClient);
            return socketClient;
        }
    }

    private void disconnect(String url,int port){
        SocketClient socketClient = getSocket(url,port);
        if (socketClient!=null){
            socketClient.disconnect();
            socketClients.remove(url+":"+port);
        }
    }

    private SocketClient getSocket(String url,int port){
        String key = url+":"+port;
        return socketClients.get(key);
    }


    IBinder getBinder() {
        return binder;
    }

    void destroy() {
        if (serverMessenger != null) {
            serverMessenger = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (handlerThread != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                handlerThread.quitSafely();
            }else {
                handlerThread.quit();
            }
            handlerThread = null;
        }
    }

    private void sendMessage(int messageType, Data data) {
        if (clientMessenger == null) {
            Logger.e("clientMessenger is null");
            return;
        }
        Message msgForClient = Message.obtain();
        msgForClient.what = messageType;
        Bundle bundle = new Bundle();
        bundle.putParcelable(MessageKey.KEY_DATA,data);
        msgForClient.setData(bundle);
        try {
            clientMessenger.send(msgForClient);
        } catch (RemoteException e) {
            Logger.e("server send message error.\r\nclientMessenger send error:"+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceive(String url, int port, String message) {
        Logger.e("socket receive:\r\n"+url+":"+port+"\r\nmessage:"+message);
        sendMessage(MessageType.MESSAGE,new Data(url,port,message));
    }

    @Override
    public void onConnected(String url, int port) {
        Logger.e("socket receive connected:\r\n"+url+":"+port+"\r\n");
        sendMessage(MessageType.CONNECT_SUCCESS,new Data(url,port,null));
    }
}
