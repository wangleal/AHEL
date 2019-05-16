package wang.leal.ahel.http.exception;

public class HttpException extends RuntimeException {

    private int code;
    private String body;
    public HttpException(int code, String message, String body){
        super(message);
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }

}
