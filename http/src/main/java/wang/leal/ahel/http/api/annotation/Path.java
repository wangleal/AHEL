package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Named replacement in a URL path segment.
 *
 * <p>Simple example:
 *
 * <pre><code>
 * &#64;GET
 * Call&lt;ResponseBody&gt; example(@Url String url,@Path("id") int id);
 * </code></pre>
 *
 * Calling with {@code foo.example("http://www.a.b/image/{id}",1)} yields {@code http://www.a.b/image/1}.
 *
 * <p>Values are URL encoded by default. Disable with {@code encoded=true}.
 *
 * <pre><code>
 * &#64;GET("/user/{name}")
 * Observable&lt;T&gt; encoded(@Path("name") String name);
 *
 * &#64;GET("/user/{name}")
 * Observable&lt;T&gt; notEncoded(@Path(value="name", encoded=true) String name);
 * </code></pre>
 *
 * Calling {@code foo.encoded("John+Doe")} yields {@code /user/John%2BDoe} whereas {@code
 * foo.notEncoded("John+Doe")} yields {@code /user/John+Doe}.
 *
 * <p>Path parameters may not be {@code null}.
 */
@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Path {
    String value();

    /**
     * Specifies whether the argument value to the annotated method parameter is already URL encoded.
     */
    boolean encoded() default false;
}

