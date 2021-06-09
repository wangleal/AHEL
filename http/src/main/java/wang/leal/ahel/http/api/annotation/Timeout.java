package wang.leal.ahel.http.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** The timeout.
 *  MILLISECONDS by Int.
 **/
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Timeout {}
