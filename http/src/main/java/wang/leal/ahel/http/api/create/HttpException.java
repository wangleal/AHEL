package wang.leal.ahel.http.api.create;

/** Exception for an unexpected, non-2xx HTTP response. */
public class HttpException extends RuntimeException {
  private static String getMessage(int code,String message) {
    return "HTTP " + code + " " + message;
  }

  private final int code;
  private final String message;

  public HttpException(int code,String message) {
    super(getMessage(code,message));
    this.code = code;
    this.message = message;
  }

  /** HTTP status code. */
  public int code() {
    return code;
  }

  /** HTTP status message. */
  public String message() {
    return message;
  }
}
