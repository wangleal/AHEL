package wang.leal.ahel.http.api.create;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

final class RequestBuilder {
  private static final char[] HEX_DIGITS = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
  };
  private static final String PATH_SEGMENT_ALWAYS_ENCODE_SET = " \"<>^`{}|\\?#";

  /**
   * Matches strings that contain {@code .} or {@code ..} as a complete path segment. This also
   * matches dots in their percent-encoded form, {@code %2E}.
   *
   * <p>It is okay to have these strings within a larger path segment (like {@code a..z} or {@code
   * index.html}) but when alone they have a special meaning. A single dot resolves to no path
   * segment so {@code /one/./three/} becomes {@code /one/three/}. A double-dot pops the preceding
   * directory, so {@code /one/../three/} becomes {@code /three/}.
   *
   * <p>We forbid these in Retrofit paths because they're likely to have the unintended effect. For
   * example, passing {@code ..} to {@code DELETE /account/book/{isbn}/} yields {@code DELETE
   * /account/}.
   */
  private static final Pattern PATH_TRAVERSAL = Pattern.compile("(.*/)?(\\.|%2e|%2E){1,2}(/.*)?");

  private final String method;

  private String url;
  private @Nullable HttpUrl.Builder urlBuilder;

  private final Request.Builder requestBuilder;
  private final Headers.Builder headersBuilder;
  private @Nullable MediaType contentType;

  private final boolean hasBody;
  private @Nullable MultipartBody.Builder multipartBuilder;
  private @Nullable FormBody.Builder formBuilder;
  private @Nullable RequestBody body;

  RequestBuilder(
      String method,
      String url,
      @Nullable Headers headers,
      @Nullable MediaType contentType,
      boolean hasBody,
      boolean isFormEncoded,
      boolean isMultipart) {
    this.method = method;
    this.url = url;
    this.requestBuilder = new Request.Builder();
    this.contentType = contentType;
    this.hasBody = hasBody;

    if (headers != null) {
      headersBuilder = headers.newBuilder();
    } else {
      headersBuilder = new Headers.Builder();
    }

    if (isFormEncoded) {
      // Will be set to 'body' in 'build'.
      formBuilder = new FormBody.Builder();
    } else if (isMultipart) {
      // Will be set to 'body' in 'build'.
      multipartBuilder = new MultipartBody.Builder();
      multipartBuilder.setType(MultipartBody.FORM);
    }
  }

  void setUrl(String url) {
    this.url = url;
  }

  void addHeader(String name, String value) {
    if ("Content-Type".equalsIgnoreCase(name)) {
      try {
        contentType = MediaType.get(value);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Malformed content type: " + value, e);
      }
    } else {
      headersBuilder.add(name, value);
    }
  }

  void addHeaders(Headers headers) {
    headersBuilder.addAll(headers);
  }

  void addPathParam(String name, String value, boolean encoded) throws IOException {
    String replacement = canonicalizeForPath(value, encoded);
    String newUrl = url.replace("{" + name + "}", replacement);
    if (PATH_TRAVERSAL.matcher(newUrl).matches()) {
      throw new IllegalArgumentException(
          "@Path parameters shouldn't perform path traversal ('.' or '..'): " + value);
    }
    url = newUrl;
  }

  private static String canonicalizeForPath(String input, boolean alreadyEncoded) throws IOException {
    int codePoint;
    for (int i = 0, limit = input.length(); i < limit; i += Character.charCount(codePoint)) {
      codePoint = input.codePointAt(i);
      if (codePoint < 0x20
          || codePoint >= 0x7f
          || PATH_SEGMENT_ALWAYS_ENCODE_SET.indexOf(codePoint) != -1
          || (!alreadyEncoded && (codePoint == '/' || codePoint == '%'))) {
        // Slow path: the character at i requires encoding!
        Buffer out = new Buffer();
        out.writeUtf8(input, 0, i);
        canonicalizeForPath(out, input, i, limit, alreadyEncoded);
        return out.readUtf8();
      }
    }

    // Fast path: no characters required encoding.
    return input;
  }

  private static void canonicalizeForPath(
      Buffer out, String input, int pos, int limit, boolean alreadyEncoded) throws IOException {
    Buffer utf8Buffer = null; // Lazily allocated.
    int codePoint;
    for (int i = pos; i < limit; i += Character.charCount(codePoint)) {
      codePoint = input.codePointAt(i);
      if (alreadyEncoded
          && (codePoint == '\t' || codePoint == '\n' || codePoint == '\f' || codePoint == '\r')) {
        // Skip this character.
      } else if (codePoint < 0x20
          || codePoint >= 0x7f
          || PATH_SEGMENT_ALWAYS_ENCODE_SET.indexOf(codePoint) != -1
          || (!alreadyEncoded && (codePoint == '/' || codePoint == '%'))) {
        // Percent encode this character.
        if (utf8Buffer == null) {
          utf8Buffer = new Buffer();
        }
        utf8Buffer.writeUtf8CodePoint(codePoint);
        while (!utf8Buffer.exhausted()) {
          int b = utf8Buffer.readByte() & 0xff;
          out.writeByte('%');
          out.writeByte(HEX_DIGITS[(b >> 4) & 0xf]);
          out.writeByte(HEX_DIGITS[b & 0xf]);
        }
      } else {
        // This character doesn't need encoding. Just copy it over.
        out.writeUtf8CodePoint(codePoint);
      }
    }
  }

  void addQueryParam(String name, @Nullable String value, boolean encoded) {
    urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();

    if (encoded) {
      urlBuilder.addEncodedQueryParameter(name, value);
    } else {
      urlBuilder.addQueryParameter(name, value);
    }
  }

  @SuppressWarnings("ConstantConditions") // Only called when isFormEncoded was true.
  void addFormField(String name, String value, boolean encoded) {
    if (encoded) {
      formBuilder.addEncoded(name, value);
    } else {
      formBuilder.add(name, value);
    }
  }

  @SuppressWarnings("ConstantConditions") // Only called when isMultipart was true.
  void addPart(Headers headers, RequestBody body) {
    multipartBuilder.addPart(headers, body);
  }

  @SuppressWarnings("ConstantConditions") // Only called when isMultipart was true.
  void addPart(MultipartBody.Part part) {
    multipartBuilder.addPart(part);
  }

  void setBody(RequestBody body) {
    this.body = body;
  }

  <T> void addTag(Class<T> cls, @Nullable T value) {
    requestBuilder.tag(cls, value);
  }

  Request.Builder get() {
    HttpUrl url;
    HttpUrl.Builder urlBuilder = this.urlBuilder;
    if (urlBuilder != null) {
      url = urlBuilder.build();
    }else {
      url = HttpUrl.parse(this.url);
    }

    if (url == null) {
      throw new IllegalArgumentException(
              "Url can not be null");
    }

    RequestBody body = this.body;
    if (body == null) {
      // Try to pull from one of the builders.
      if (formBuilder != null) {
        body = formBuilder.build();
      } else if (multipartBuilder != null) {
        body = multipartBuilder.build();
      } else if (hasBody) {
        // Body is absent, make an empty body.
        body = RequestBody.create(null,new byte[0]);
      }
    }

    MediaType contentType = this.contentType;
    if (contentType != null) {
      if (body != null) {
        body = new ContentTypeOverridingRequestBody(body, contentType);
      } else {
        headersBuilder.add("Content-Type", contentType.toString());
      }
    }

    return requestBuilder.url(url).headers(headersBuilder.build()).method(method, body);
  }

  private static class ContentTypeOverridingRequestBody extends RequestBody {
    private final RequestBody delegate;
    private final MediaType contentType;

    ContentTypeOverridingRequestBody(RequestBody delegate, MediaType contentType) {
      this.delegate = delegate;
      this.contentType = contentType;
    }

    @Override
    public MediaType contentType() {
      return contentType;
    }

    @Override
    public long contentLength() throws IOException {
      return delegate.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
      delegate.writeTo(sink);
    }
  }
}
