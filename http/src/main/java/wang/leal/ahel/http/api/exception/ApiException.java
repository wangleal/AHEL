package wang.leal.ahel.http.api.exception;

/**
 * Api Exception
 * Created by wang leal on 2016/6/20.
 */
public class ApiException extends RuntimeException {

    private int code;
    private String message;
    private String body;
    public ApiException(int code, String message,String body){
        super("code:"+code+"\r\n"+"message:"+message+"\r\n"+"body:"+body);
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getBody() {
        return body;
    }
}
