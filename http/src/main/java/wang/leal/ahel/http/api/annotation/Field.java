package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Named pair for a form-encoded request.
 *
 * <p>Simple Example:
 *
 * <pre><code>
 * &#64;FormUrlEncoded
 * &#64;POST
 * Observable&lt;T&gt; example(
 *     &#64;Field("name") String name,
 *     &#64;Field("occupation") String occupation);
 * </code></pre>
 *
 * Calling with {@code foo.example("Bob Smith", "President")} yields a request body of {@code
 * name=Bob+Smith&occupation=President}.
 *
 * <p>Array/Varargs Example:
 *
 * <pre><code>
 * &#64;FormUrlEncoded
 * &#64;POST
 * Observable&lt;T&gt; example(@Field("name") String... names);
 * </code></pre>
 *
 * Calling with {@code foo.example("Bob Smith", "Jane Doe")} yields a request body of {@code
 * name=Bob+Smith&name=Jane+Doe}.
 *
 * @see FormUrlEncoded
 * @see FieldMap
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Field {
  String value();

  /** Specifies whether the {@linkplain #value() name} and value are already URL encoded. */
  boolean encoded() default false;
}
