package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A full endpoint URL.
 *
 * <pre><code>
 * &#64;GET
 * Observable&lt;T&gt; list(@Url String url);
 * </code></pre>
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Url {}
