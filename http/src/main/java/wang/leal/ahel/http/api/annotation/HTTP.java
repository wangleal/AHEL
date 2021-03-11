package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import okhttp3.HttpUrl;
import retrofit2.http.Url;

/**
 * Use a custom HTTP verb for a request.
 *
 * <pre><code>
 * interface Service {
 *   &#064;HTTP(method = "CUSTOM")
 *   Observable&lt;T&gt; customEndpoint();
 * }
 * </code></pre>
 *
 * This annotation can also used for sending {@code DELETE} with a request body:
 *
 * <pre><code>
 * interface Service {
 *   &#064;HTTP(method = "DELETE", hasBody = true)
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
   * A key of Url.
   */
  String key() default "";
  boolean hasBody() default false;
}
