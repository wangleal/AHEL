package wang.leal.ahel.socket.process;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import wang.leal.ahel.socket.SocketStatusListener;
import wang.leal.ahel.socket.log.Logger;
import wang.leal.ahel.socket.processor.LocalProcessor;
import wang.leal.ahel.socket.processor.RemoteProcessor;

public class Client {
    private static final Client INSTANCE = new Client();
    private Map<String,Subject<String>> appSubjects = new HashMap<>();
    private Map<String, LocalProcessor> requestProcessor = new HashMap<>();
    private Map<String, RemoteProcessor> receiveProcessor = new HashMap<>();
    private Map<String, SocketStatusListener> socketStatusListeners = new HashMap<>();
    private Messenger clientMessenger;
    private Messenger serviceMessenger;
    private ServiceConnection serviceConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.e("service connected");
            if (serviceMessenger!=null){
                return;
            }
            if (service!=null) {
                serviceMessenger = new Messenger(service);
                Logger.e("client send ping");
                sendMessage(MessageType.PING,null);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.e("service disconnected");
        }
    };

    private Client(){}

    public static Client getInstance(){
        return INSTANCE;
    }

    public void startSocketProcess(Context context, final ProcessListener listener){
        if (!isStartProcess(context)){
            return;
        }
        Intent intent = new Intent(context, SocketService.class);
        HandlerThread handlerThread = new HandlerThread("Client");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                bundle.setClassLoader(getClass().getClassLoader());
                Data data = bundle.getParcelable(MessageKey.KEY_DATA);
                switch (msg.what){
                    case MessageType.PONG:
                        Logger.e("client receive pong");
                        if (listener!=null){
                            listener.onConnected();
                        }
                        break;
                    case MessageType.MESSAGE:
                        Logger.e("client receive message:"+data.message);
                        RemoteProcessor processor = getReceiveProcessor(data.url, data.port);
                        if (processor!=null){
                            List<String> messages = processor.handleMessage(data.message);
                            if (messages!=null&&messages.size()>0){
                                for (String message:messages){
                                    Logger.e("receive processor handle message:"+message);
                                    if (!TextUtils.isEmpty(message)){
                                        Subject<String> subject = getSubject(data.url,data.port);
                                        if (subject!=null){
                                            subject.onNext(message);
                                        }
                                    }
                                }
                            }
                        }else {
                            Subject<String> subject = getSubject(data.url,data.port);
                            if (subject!=null){
                                subject.onNext(data.message);
                            }
                        }
                        break;
                    case MessageType.CONNECT_SUCCESS:
                        SocketStatusListener socketStatusListener = getSocketStatusListener(data.url,data.port);
                        if (socketStatusListener!=null){
                            socketStatusListener.onConnected();
                        }
                        Logger.e("client receive connected");
                        break;
                }
            }
        };
        clientMessenger = new Messenger(handler);
        context.bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
    }

    //判断是否是开启进程
    private boolean isStartProcess(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am==null){
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return !procInfo.processName.endsWith(":socket");
            }
        }
        return true;
    }
    public void stopSocketProcess(Context context){
        appSubjects.clear();
        context.unbindService(serviceConnection);
    }

    public Observable<String> registerMessage(String url, int port){
        Subject<String> subject = getSubject(url,port);
        if (subject!=null){
            return subject;
        }else {
            return null;
        }
    }

    private void setSubject(String url,int port){
        if (getSubject(url,port)==null){
            appSubjects.put(url+":"+port,PublishSubject.<String>create().toSerialized());
        }
    }

    private Subject<String> getSubject(String url,int port){
        return appSubjects.get(url+":"+port);
    }

    private void setRequestProcessor(String url, int port, LocalProcessor requestProcessor) {
        this.requestProcessor.put(url+":"+port,requestProcessor);
    }

    private void setReceiveProcessor(String url, int port, RemoteProcessor receiveProcessor) {
        this.receiveProcessor.put(url+":"+port,receiveProcessor);
    }

    private void setSocketStatusListener(String url, int port, SocketStatusListener socketStatusListener){
        this.socketStatusListeners.put(url+":"+port,socketStatusListener);
    }

    private LocalProcessor getRequestProcessor(String url, int port) {
        return requestProcessor.get(url+":"+port);
    }

    private RemoteProcessor getReceiveProcessor(String url, int port){
        return receiveProcessor.get(url+":"+port);
    }

    private SocketStatusListener getSocketStatusListener(String url,int port) {
        return socketStatusListeners.get(url+":"+port);
    }

    private void sendMessage(int messageType, Data data){
        if (serviceMessenger==null){
            Logger.e("serviceMessenger or data is null");
            return;
        }
        Message msgToServer = Message.obtain();
        msgToServer.what = messageType;
        if (data!=null){
            Bundle bundle = new Bundle();
            Data processData = new Data();
            processData.url = data.url;
            processData.port = data.port;
            processData.message = data.message;
            if (messageType==MessageType.CONNECT){
                if (TextUtils.isEmpty(data.url)){
                    Logger.e("data.url is null");
                    return;
                }
            }else if (messageType==MessageType.MESSAGE){
                LocalProcessor processor = getRequestProcessor(data.url,data.port);
                if (processor!=null){
                    processData.message = processor.handleMessage(data.message);
                }
            }
            bundle.putParcelable(MessageKey.KEY_DATA,processData);
            msgToServer.setData(bundle);
        }
        msgToServer.replyTo = clientMessenger;
        try {
            Logger.e("client send message to server");
            serviceMessenger.send(msgToServer);
        } catch (RemoteException e) {
            Logger.e("client send message error.\r\nserviceMessenger send error:"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(Data data){
        sendMessage(MessageType.MESSAGE,data);
    }

    public void connect(String url,int port){
        connect(url,port,null);
    }

    public void connect(String url,int port,SocketStatusListener socketStatusListener){
        connect(url,port,socketStatusListener,null,null);
    }

    public void connect(String url,int port,LocalProcessor localProcessor,RemoteProcessor remoteProcessor){
        connect(url,port,null,localProcessor,remoteProcessor);
    }

    public void connect(String url,int port,SocketStatusListener socketStatusListener,LocalProcessor localProcessor,RemoteProcessor remoteProcessor){
        setSubject(url,port);
        setSocketStatusListener(url,port,socketStatusListener);
        setRequestProcessor(url,port,localProcessor);
        setReceiveProcessor(url,port,remoteProcessor);
        sendMessage(MessageType.CONNECT,new Data(url,port,null));
    }

    public void disconnect(String url,int port){
        sendMessage(MessageType.DISCONNECT,new Data(url,port,null));
        String key = url+":"+port;
        appSubjects.remove(key);
        socketStatusListeners.remove(key);
        requestProcessor.remove(key);
        receiveProcessor.remove(key);
    }
}
