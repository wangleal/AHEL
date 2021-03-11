package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Make a HEAD request. */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HEAD {
    /**
     * A key of Url
     */
    String value() default "";
}
