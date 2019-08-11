package wang.leal.ahel.sample.socket;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import wang.leal.ahel.socket.Socket;
import wang.leal.ahel.socket.SocketStatusListener;

public class Connection {

    public static void connect(String url, int port, SocketStatusListener socketStatusListener){
        Socket.connectOrGet(url,port,socketStatusListener,new RequestProcessor("10000042","10000049"),new ReceiveProcessor());
    }

    public static Observable<String> registerMessage(String url, int port){
        return Socket.connectOrGet(url,port).registerMessage();
    }

    public static void sendAuth(String url,int port){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",Type.Request.AUTH);
            jsonObject.put("message","ping");
            Socket.connectOrGet(url,port).sendMessage(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String url,int port,String message){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",Type.Request.MESSAGE);
            jsonObject.put("message",message);
            Socket.connectOrGet(url,port).sendMessage(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(String url,int port){
        Socket.connectOrGet(url,port).disconnect();
    }

    public static void init(Context context){
        Socket.startProcess(context.getApplicationContext());
    }
}
