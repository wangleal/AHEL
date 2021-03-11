package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * Adds headers specified in the {@link Map} or {@link okhttp3.Headers}.
 *
 * <p>Simple Example:
 *
 * <pre>
 * &#64;GET
 * void list(@HeaderMap Map&lt;String, String&gt; headers);
 *
 * ...
 *
 * // The following call yields /search with headers
 * // Accept: text/plain and Accept-Charset: utf-8
 * foo.list(ImmutableMap.of("Accept", "text/plain", "Accept-Charset", "utf-8"));
 * </pre>
 *
 * @see Header
 * @see Headers
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface HeaderMap {}
