package wang.leal.ahel.sample.socket;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.Charset;
import java.util.Arrays;

import wang.leal.ahel.utils.ByteArrayUtil;
import wang.leal.ahel.utils.StringUtil;

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
    private byte[] body;

    static CMDMessage getInstance() {
        return INSTANCE;
    }

    String getReceiveMessage(byte[] message){
        String receive = dealMessage(message);
        if (receive==null){
            messageIndex = 0;
        }
        return receive;
    }

    private String dealMessage(byte[] message){
        if (message==null||message.length<=0){
            return null;
        }
        int length = message.length;
        if (messageIndex>=length){
            return null;
        }
        int verLength = 2;
        if (TextUtils.isEmpty(ver)){
            if (length>=messageIndex+verLength){
                this.ver = ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+verLength);
                messageIndex = messageIndex+verLength;
            }else {
                this.ver = ByteArrayUtil.subByteArray(message,messageIndex);
                return null;
            }
        }else {
            if (StringUtil.getStringByteLength(ver)<verLength){
                this.ver = this.ver+ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+1);
                messageIndex = messageIndex+1;
            }
        }
        int typLength = 1;
        if (TextUtils.isEmpty(typ)){
            if (length>=messageIndex+typLength){
                this.typ = ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+typLength);
                messageIndex = messageIndex+typLength;
            }
        }
        int optLength = 1;
        if (TextUtils.isEmpty(opt)){
            if (length>=messageIndex+optLength){
                this.opt = ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+optLength);
                messageIndex = messageIndex+optLength;
            }
        }
        int cmdLength = 4;
        if (TextUtils.isEmpty(cmd)){
            if (length>=messageIndex+cmdLength){
                this.cmd = ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+cmdLength);
                messageIndex = messageIndex+cmdLength;
            }else {
                this.cmd = ByteArrayUtil.subByteArray(message,messageIndex);
                return null;
            }
        }else {
            if (StringUtil.getStringByteLength(cmd)<cmdLength){
                int diff = cmdLength-StringUtil.getStringByteLength(cmd);
                if (length>=messageIndex+diff){
                    this.cmd = this.cmd+ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+diff);
                    messageIndex = messageIndex+diff;
                }else {
                    this.cmd = this.cmd+ByteArrayUtil.subByteArray(message,messageIndex);
                    return null;
                }
            }
        }
        int seqLength = 8;
        if (TextUtils.isEmpty(seq)){
            if (length>=messageIndex+seqLength){
                this.seq = ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+seqLength);
                messageIndex = messageIndex+seqLength;
            }else {
                this.seq = ByteArrayUtil.subByteArray(message,messageIndex);
                return null;
            }
        }else {
            if (StringUtil.getStringByteLength(seq)<seqLength){
                int diff = seqLength-StringUtil.getStringByteLength(seq);
                if (length>=messageIndex+diff){
                    this.seq = this.seq+ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+diff);
                    messageIndex = messageIndex+diff;
                }else {
                    this.seq = this.seq+ByteArrayUtil.subByteArray(message,messageIndex);
                    return null;
                }
            }
        }
        int lenLength = 4;
        if (TextUtils.isEmpty(len)){
            if (length>=messageIndex+lenLength){
                this.len = ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+lenLength);
                messageIndex = messageIndex+lenLength;
            }else {
                this.len = ByteArrayUtil.subByteArray(message,messageIndex);
                return null;
            }
        }else {
            if (StringUtil.getStringByteLength(len)<lenLength){
                int diff = lenLength-StringUtil.getStringByteLength(len);
                if (length>=messageIndex+diff){
                    this.len = this.len+ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+diff);
                    messageIndex = messageIndex+diff;
                }else {
                    this.len = this.len+ByteArrayUtil.subByteArray(message,messageIndex);
                    return null;
                }
            }
        }

        int resLength = 8;
        if (TextUtils.isEmpty(res)){
            if (length>=messageIndex+resLength){
                this.res = ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+resLength);
                messageIndex = messageIndex+resLength;
            }else {
                this.res = ByteArrayUtil.subByteArray(message,messageIndex);
                return null;
            }
        }else {
            if (StringUtil.getStringByteLength(res)<resLength){
                int diff = resLength-StringUtil.getStringByteLength(res);
                if (length>=messageIndex+diff){
                    this.res = this.res+ByteArrayUtil.subByteArray(message,messageIndex,messageIndex+diff);
                    messageIndex = messageIndex+diff;
                }else {
                    this.res = this.res+ByteArrayUtil.subByteArray(message,messageIndex);
                    return null;
                }
            }
        }

        if (!len.matches("\\d+")){
            return null;
        }
        int bodyLength = Integer.valueOf(len);
        if (body==null||body.length<=0){
            if (length>=messageIndex+bodyLength){
                this.body = Arrays.copyOfRange(message, messageIndex, messageIndex+bodyLength);
                messageIndex = messageIndex+bodyLength;
            }else {
                this.body = Arrays.copyOfRange(message, messageIndex, length);
                return null;
            }
        }else {
            if (body.length<bodyLength){
                int diff = bodyLength-body.length;
                if (length>=messageIndex+diff){
                    this.body =ByteArrayUtil.concat(this.body,Arrays.copyOfRange(message, messageIndex, messageIndex+diff));
                    messageIndex = messageIndex+diff;
                }else {
                    this.body = ByteArrayUtil.concat(this.body,Arrays.copyOfRange(message, messageIndex, length));
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
            String data = new String(body,Charset.forName("UTF-8"));
            JSONTokener jsonTokener = new JSONTokener(data);
            if (jsonTokener.nextValue() instanceof JSONObject){
                JSONObject bodyJson = new JSONObject(data);
                jsonObject.put("data",bodyJson);
            }else {
                jsonObject.put("data",body);
            }
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            this.ver = null;
            this.typ = null;
            this.opt = null;
            this.cmd = null;
            this.seq = null;
            this.len = null;
            this.res = null;
            this.body = null;
        }
        return null;
    }

    private StringBuffer stringBuffer = new StringBuffer();
    String getRequestMessage(String request){
        stringBuffer.setLength(0);
        String len;
        int length = StringUtil.getStringByteLength(request);
        if (length>9999){
            len = "9999";
            this.body = Arrays.copyOfRange(request.getBytes(Charset.forName("UTF-8")),0,9999);
        }else if (length>999){
            len = length+"";
        }else if (length>99){
            len = "0"+length;
        }else if (length>9){
            len = "00"+length;
        }else {
            len = "000"+length;
        }
        stringBuffer.append("10")
                .append("S")
                .append("U")
                .append(1000)
                .append("12345678")
                .append(len)
                .append("00000000")
                .append(new String(body,Charset.forName("UTF-8")));
        return stringBuffer.toString();
    }

}
