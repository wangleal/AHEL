package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Denotes that the request body is multi-part. Parts should be declared as parameters and annotated
 * with {@link Part @Part}.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Multipart {}
