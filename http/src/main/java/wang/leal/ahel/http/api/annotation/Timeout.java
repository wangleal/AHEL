package wang.leal.ahel.http.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** The timeout.
 *  MILLISECONDS by Int.
 **/
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Timeout {
    /**
     * The timeout of milliseconds.
     */
    int duration();
    TimeUnit unit();
}
