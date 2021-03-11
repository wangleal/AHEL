package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Query parameter appended to the URL.
 *
 * <p>Simple Example:
 *
 * <pre><code>
 * &#64;GET
 * Observable&lt;T&gt; friends(@Query("page") int page);
 * </code></pre>
 *
 * Calling with {@code foo.friends(1)} yields {@code /friends?page=1}.
 *
 * <p>Example with {@code null}:
 *
 * <pre><code>
 * &#64;GET
 * Observable&lt;T&gt; friends(@Query("group") String group);
 * </code></pre>
 *
 * Calling with {@code foo.friends(null)} yields {@code /friends}.
 *
 * <p>Array/Varargs Example:
 *
 * <pre><code>
 * &#64;GET
 * Observable&lt;T&gt; friends(@Query("group") String... groups);
 * </code></pre>
 *
 * Calling with {@code foo.friends("coworker", "bowling")} yields {@code
 * /friends?group=coworker&group=bowling}.
 *
 * <p>Parameter names and values are URL encoded by default. Specify {@link #encoded() encoded=true}
 * to change this behavior.
 *
 * <pre><code>
 * &#64;GET
 * Observable&lt;T&gt; friends(@Query(value="group", encoded=true) String group);
 * </code></pre>
 *
 * Calling with {@code foo.friends("foo+bar"))} yields {@code /friends?group=foo+bar}.
 *
 * @see QueryMap
 * @see QueryName
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Query {
  /** The query parameter name. */
  String value();

  /**
   * Specifies whether the parameter {@linkplain #value() name} and value are already URL encoded.
   */
  boolean encoded() default false;
}
