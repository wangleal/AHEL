package wang.leal.ahel.http.api.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Denotes a single part of a multi-part request.
 *
 * <p>The parameter type on which this annotation exists will be processed in one of two ways:
 *
 * <ul>
 *   <li>If the type is {@link okhttp3.MultipartBody.Part} the contents will be used directly. Omit
 *       the name from the annotation (i.e., {@code @Part MultipartBody.Part part}).
 *   <li>If the type is {@link okhttp3.RequestBody RequestBody} the value will be used directly with
 *       its content type. Supply the part name in the annotation (e.g., {@code @Part("foo")
 *       RequestBody foo}).
 * </ul>
 *
 * <p>Values may be {@code null} which will omit them from the request body.
 *
 * <p>
 *
 * <pre><code>
 * &#64;Multipart
 * &#64;POST
 * Observable&lt;T&gt; example(
 *     &#64;Part("description") String description,
 *     &#64;Part(value = "image", encoding = "8-bit") RequestBody image);
 * </code></pre>
 *
 * <p>Part parameters may not be {@code null}.
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Part {
  /**
   * The name of the part. Required for all parameter types except {@link
   * okhttp3.MultipartBody.Part}.
   */
  String value() default "";
  /** The {@code Content-Transfer-Encoding} of this part. */
  String encoding() default "binary";
}
