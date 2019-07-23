package wang.leal.ahel.sample.socket;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import wang.leal.ahel.socket.Socket;

public class Connection {

    public static void connect(String url,int port){
        Socket.connectOrGet(url,port,new RequestProcessor("12345678","12345678"),new ReceiveProcessor());
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
}
