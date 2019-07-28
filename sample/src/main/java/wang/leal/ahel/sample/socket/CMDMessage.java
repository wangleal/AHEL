package wang.leal.ahel.sample.socket;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * > 例: 10S11000123456780004RESERVEDPING // ping 消息
 * > ver  2  "10"
 * > typ  1  'S' 二进制头
 * > opt  1  'U' 普通上行消息
 * >         'D' 普通下行消息
 * >         'W' 消息需要ack
 * >         'A' ack消息
 * > cmd  4      命令号 1000,  cmd = "1000"
 * > seq  8      待确认消息sequence, "12345678"
 * > len  4      长度, "0004" 代表header后有4个字符
 * > res  8      预留
 * > body        ping
 */
class CMDMessage {
    private static final CMDMessage INSTANCE = new CMDMessage();
    private int messageIndex = 0;
    private String ver;
    private String typ;
    private String opt;
    private String cmd;
    private String seq;
    private String len;
    private String res;
    private String body;

    static CMDMessage getInstance() {
        return INSTANCE;
    }

    String getReceiveMessage(String message){
        String receive = dealMessage(message);
        if (receive==null){
            messageIndex = 0;
        }
        return receive;
    }

    private String dealMessage(String message){
        if (TextUtils.isEmpty(message)){
            return null;
        }
        if (messageIndex>=message.length()){
            return null;
        }
        int verLength = 2;
        if (TextUtils.isEmpty(ver)){
            if (message.length()>=messageIndex+verLength){
                this.ver = message.substring(messageIndex,messageIndex+verLength);
                messageIndex = messageIndex+verLength;
            }else {
                this.ver = message.substring(messageIndex);
                return null;
            }
        }else {
            if (ver.length()<verLength){
                this.ver = this.ver+message.substring(messageIndex,messageIndex+1);
                messageIndex = messageIndex+1;
            }
        }
        int typLength = 1;
        if (TextUtils.isEmpty(typ)){
            if (message.length()>=messageIndex+typLength){
                this.typ = message.substring(messageIndex,messageIndex+typLength);
                messageIndex = messageIndex+typLength;
            }
        }
        int optLength = 1;
        if (TextUtils.isEmpty(opt)){
            if (message.length()>=messageIndex+optLength){
                this.opt = message.substring(messageIndex,messageIndex+optLength);
                messageIndex = messageIndex+optLength;
            }
        }
        int cmdLength = 4;
        if (TextUtils.isEmpty(cmd)){
            if (message.length()>=messageIndex+cmdLength){
                this.cmd = message.substring(messageIndex,messageIndex+cmdLength);
                messageIndex = messageIndex+cmdLength;
            }else {
                this.cmd = message.substring(messageIndex);
                return null;
            }
        }else {
            if (cmd.length()<cmdLength){
                int diff = cmdLength-cmd.length();
                if (message.length()>=messageIndex+diff){
                    this.cmd = this.cmd+message.substring(messageIndex,messageIndex+diff);
                    messageIndex = messageIndex+diff;
                }else {
                    this.cmd = this.cmd+message.substring(messageIndex);
                    return null;
                }
            }
        }
        int seqLength = 8;
        if (TextUtils.isEmpty(seq)){
            if (message.length()>=messageIndex+seqLength){
                this.seq = message.substring(messageIndex,messageIndex+seqLength);
                messageIndex = messageIndex+seqLength;
            }else {
                this.seq = message.substring(messageIndex);
                return null;
            }
        }else {
            if (seq.length()<seqLength){
                int diff = seqLength-seq.length();
                if (message.length()>=messageIndex+diff){
                    this.seq = this.seq+message.substring(messageIndex,messageIndex+diff);
                    messageIndex = messageIndex+diff;
                }else {
                    this.seq = this.seq+message.substring(messageIndex);
                    return null;
                }
            }
        }
        int lenLength = 4;
        if (TextUtils.isEmpty(len)){
            if (message.length()>=messageIndex+lenLength){
                this.len = message.substring(messageIndex,messageIndex+lenLength);
                messageIndex = messageIndex+lenLength;
            }else {
                this.len = message.substring(messageIndex);
                return null;
            }
        }else {
            if (len.length()<lenLength){
                int diff = lenLength-len.length();
                if (message.length()>=messageIndex+diff){
                    this.len = this.len+message.substring(messageIndex,messageIndex+diff);
                    messageIndex = messageIndex+diff;
                }else {
                    this.len = this.len+message.substring(messageIndex);
                    return null;
                }
            }
        }

        int resLength = 8;
        if (TextUtils.isEmpty(res)){
            if (message.length()>=messageIndex+resLength){
                this.res = message.substring(messageIndex,messageIndex+resLength);
                messageIndex = messageIndex+resLength;
            }else {
                this.res = message.substring(messageIndex);
                return null;
            }
        }else {
            if (res.length()<resLength){
                int diff = resLength-res.length();
                if (message.length()>=messageIndex+diff){
                    this.res = this.res+message.substring(messageIndex,messageIndex+diff);
                    messageIndex = messageIndex+diff;
                }else {
                    this.res = this.res+message.substring(messageIndex);
                    return null;
                }
            }
        }

        if (!len.matches("\\d+")){
            return null;
        }
        int bodyLength = Integer.valueOf(len);
        if (TextUtils.isEmpty(body)){
            if (message.length()>=messageIndex+bodyLength){
                this.body = message.substring(messageIndex,messageIndex+bodyLength);
                messageIndex = messageIndex+bodyLength;
            }else {
                this.body = message.substring(messageIndex);
                return null;
            }
        }else {
            if (body.length()<bodyLength){
                int diff = bodyLength-body.length();
                if (message.length()>=messageIndex+diff){
                    this.body = this.body+message.substring(messageIndex,messageIndex+diff);
                    messageIndex = messageIndex+diff;
                }else {
                    this.body = this.body+message.substring(messageIndex);
                    return null;
                }
            }
        }

        if (!cmd.matches("\\d+")){
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cmd",cmd);
            jsonObject.put("data",body);
            this.ver = null;
            this.typ = null;
            this.opt = null;
            this.cmd = null;
            this.seq = null;
            this.len = null;
            this.res = null;
            this.body = null;
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringBuffer stringBuffer = new StringBuffer();
    String getRequestMessage(String request){
        stringBuffer.setLength(0);
        String len;
        if (request.length()>9999){
            len = "9999";
            this.body = request.substring(0,9999);
        }else if (request.length()>999){
            len = request.length()+"";
        }else if (request.length()>99){
            len = "0"+request.length();
        }else if (request.length()>9){
            len = "00"+request.length();
        }else {
            len = "000"+request.length();
        }
        stringBuffer.append("10")
                .append("S")
                .append("U")
                .append(1000)
                .append("12345678")
                .append(len)
                .append("00000000")
                .append(request);
        return stringBuffer.toString();
    }

}
