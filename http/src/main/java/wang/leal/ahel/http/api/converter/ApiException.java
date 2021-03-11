package wang.leal.ahel.http.api.converter;

public class ApiException extends RuntimeException {
    private static String getMessage(int code, String message, String data) {
        return "Api code:" + code + ",message:" + message + ",data:" + data;
    }

    private final int code;
    private final String message;
    private final String data;

    public ApiException(int code, String message, String data) {
        super(getMessage(code, message, data));
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public String data() {
        return data;
    }
}
