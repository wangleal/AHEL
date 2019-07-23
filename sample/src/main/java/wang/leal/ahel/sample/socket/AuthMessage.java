package wang.leal.ahel.sample.socket;

/**
 * > 例: 10S0100000006000000012345678003212345678901234567890123456789012
 * > ver   2   10
 * > typ   1   S
 * > opt   1   0
 * > uid   8   连接唯一ID, uid:10000000 => "10000000"
 * > cid   8   连接分类ID, rid:60000000 => "60000000"
 * > sign  8   签名串 12345678
 * > len   4   32 Auth包后字符串长度
 * > body      12345678901234567890123456789012
 */
class AuthMessage {
    private static final AuthMessage INSTANCE = new AuthMessage();

    private StringBuffer requestMessage = new StringBuffer();
    private String ver = "10";
    private String typ = "S";
    private String opt = "0";
    private String uid;
    private String cid;
    private String sign = "12345678";
    private String len;
    private String body;

    static AuthMessage getInstance(String uid,String cid) {
        INSTANCE.uid = uid;
        INSTANCE.cid = cid;
        return INSTANCE;
    }

    String getMessage(String request) {
        String message = this.body = request;
        if (message.length()>9999){
            this.len = "9999";
            this.body = message.substring(0,9999);
        }else if (message.length()>999){
            this.len = message.length()+"";
        }else if (message.length()>99){
            this.len = "0"+message.length();
        }else if (message.length()>9){
            this.len = "00"+message.length();
        }else {
            this.len = "000"+message.length();
        }
        requestMessage.setLength(0);
        return requestMessage.append(ver)
                .append(typ)
                .append(opt)
                .append(uid)
                .append(cid)
                .append(sign)
                .append(len)
                .append(body).toString();
    }
}
