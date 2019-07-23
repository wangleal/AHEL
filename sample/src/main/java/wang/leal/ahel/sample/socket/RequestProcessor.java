package wang.leal.ahel.sample.socket;

import org.json.JSONException;
import org.json.JSONObject;

import wang.leal.ahel.socket.processor.LocalProcessor;

public class RequestProcessor implements LocalProcessor {
    private String uid,cid;
    RequestProcessor(String uid,String cid){
        this.uid = uid;
        this.cid = cid;
    }

    @Override
    public String handleMessage(String request) {
        try {
            JSONObject jsonObject = new JSONObject(request);
            int type = jsonObject.getInt("type");
            String message = jsonObject.getString("message");
            if (type== Type.Request.AUTH){
                return AuthMessage.getInstance(this.uid,this.cid).getMessage(message);
            }else if (type==  Type.Request.MESSAGE){
                return CMDMessage.getInstance().getRequestMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}