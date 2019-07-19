package wang.leal.ahel.socket.process;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import wang.leal.ahel.socket.log.Logger;

class Server {
    private HandlerThread handlerThread;
    private Handler handler;
    private Messenger serverMessenger;
    private Messenger clientMessenger;
    private IBinder binder;

    void create() {
        handlerThread = new HandlerThread("Server");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MessageType.PING:
                        Logger.e("client receive ping");
                        clientMessenger = msg.replyTo;
                        Logger.e("client send pong");
                        Server.this.sendMessage(MessageType.PONG,null);
                        break;
                    case MessageType.CONNECT:
                        Data data = (Data) msg.obj;

                        break;
                }
            }
        };
        serverMessenger = new Messenger(handler);
        binder = serverMessenger.getBinder();
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
        Message msgForClient = Message.obtain(null, messageType, data);
        try {
            clientMessenger.send(msgForClient);
        } catch (RemoteException e) {
            Logger.e("server send message error.\r\nclientMessenger send error:"+e.getMessage());
            e.printStackTrace();
        }
    }

}
