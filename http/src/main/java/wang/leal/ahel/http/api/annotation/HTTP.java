package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Use a custom HTTP verb for a request.
 *
 * <pre><code>
 * interface Service {
 *   &#064;HTTP(method = "CUSTOM",url = "http://www.abc.com/custom/endpoint/")
 *   Observable&lt;T&gt; customEndpoint();
 * }
 * </code></pre>
 *
 * This annotation can also used for sending {@code DELETE} with a request body:
 *
 * <pre><code>
 * interface Service {
 *   &#064;HTTP(method = "DELETE", hasBody = true,url = "http://www.abc.com/custom/endpoint/")
 *   Observable&lt;T&gt; deleteObject(@Body RequestBody object);
 * }
 * </code></pre>
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HTTP {
  String method();
  /**
   * The url.
   */
  String url() default "";
  boolean hasBody() default false;
}
