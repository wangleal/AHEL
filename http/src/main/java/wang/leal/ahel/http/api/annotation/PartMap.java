package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Denotes name and value parts of a multi-part request.
 *
 * <pre><code>
 * &#64;Multipart
 * &#64;POST
 * Observable&lt;T&gt; upload(
 *     &#64;Part("file") RequestBody file,
 *     &#64;PartMap Map&lt;String, RequestBody&gt; params);
 * </code></pre>
 *
 * <p>A {@code null} value for the map, as a key, or as a value is not allowed.
 *
 * @see Multipart
 * @see Part
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface PartMap {
  /** The {@code Content-Transfer-Encoding} of the parts. */
  String encoding() default "binary";
}
