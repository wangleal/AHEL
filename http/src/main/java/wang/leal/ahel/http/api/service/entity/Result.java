package wang.leal.ahel.http.api.service.entity;

/**
 * Created by wang leal on 2017/5/23.
 */

public class Result<T> {
    private int code = -1;
    private String message;
    private T data;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
