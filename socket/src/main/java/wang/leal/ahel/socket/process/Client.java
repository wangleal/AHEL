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
import wang.leal.ahel.socket.log.Logger;

public class Client {
    private static final Client INSTANCE = new Client();
    private Map<String,Subject<String>> appSubjects = new HashMap<>();
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
                        getSubject(data.url,data.port).onNext(data.message);
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
            return subject.toSerialized();
        }else {
            return null;
        }
    }

    private Subject<String> getSubject(String url,int port){
        return appSubjects.get(url+":"+port);
    }

    private void setSubject(String url,int port){
        appSubjects.put(url+":"+port,PublishSubject.<String>create());
    }

    public void sendMessage(int messageType, Data data){
        if (serviceMessenger==null){
            Logger.e("serviceMessenger is null");
            return;
        }

        if (messageType==MessageType.CONNECT){
            if (TextUtils.isEmpty(data.url)){
                Logger.e("data.url is null");
                return;
            }
            Subject<String> subject = getSubject(data.url,data.port);
            if (subject==null){
                setSubject(data.url,data.port);
            }
        }
        Message msgToServer = Message.obtain();
        msgToServer.what = messageType;
        Bundle bundle = new Bundle();
        bundle.putParcelable(MessageKey.KEY_DATA,data);
        msgToServer.setData(bundle);
        msgToServer.replyTo = clientMessenger;
        try {
            serviceMessenger.send(msgToServer);
        } catch (RemoteException e) {
            Logger.e("client send message error.\r\nserviceMessenger send error:"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(Data data){
        sendMessage(MessageType.MESSAGE,data);
    }

}
