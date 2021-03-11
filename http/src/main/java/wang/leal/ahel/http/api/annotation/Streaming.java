package wang.leal.ahel.http.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import okhttp3.ResponseBody;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Treat the response body on methods returning {@link ResponseBody ResponseBody} as is, i.e.
 * without converting the body to {@code byte[]}.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Streaming {}
