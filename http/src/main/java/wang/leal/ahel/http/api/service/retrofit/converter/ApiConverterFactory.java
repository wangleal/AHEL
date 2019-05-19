package wang.leal.ahel.http.api.service.retrofit.converter;

import wang.leal.ahel.http.json.GsonManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * default convert factory
 * Created by wang leal on 2016/6/20.
 */
public class ApiConverterFactory extends Converter.Factory{

    private static final ScalarsConverterFactory scalarsConverterFactory = ScalarsConverterFactory.create();
    private static final GsonConverterFactory gsonConvertFactory = GsonConverterFactory.create(GsonManager.gson());

    public static ApiConverterFactory create(){
        return new ApiConverterFactory();
    }

    /**
     * Returns a {@link Converter} for converting an HTTP response body to {@code type}, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for
     * response types such as {@code SimpleResponse} from a {@code Call<SimpleResponse>}
     * declaration.
     */
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new ApiResponseConverter<>(type);
    }

    /**
     * Returns a {@link Converter} for converting {@code type} to an HTTP get body, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for types
     * specified by {@link Body @Body}, {@link Part @Part}, and {@link PartMap @PartMap}
     * values.
     */
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        //如果请求是常用类型(基本类型:int,double等,其他类型:string),用scalarsConverter来处理.否则用gson来处理.
        Converter<?,RequestBody> converter = scalarsConverterFactory.requestBodyConverter(type,parameterAnnotations,methodAnnotations,retrofit);
        if (converter==null){
            return gsonConvertFactory.requestBodyConverter(type,parameterAnnotations,methodAnnotations,retrofit);
        }else {
            return converter;
        }
    }
    /**
     * Returns a {@link Converter} for converting {@code type} to a {@link String}, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for types
     * specified by {@link Field @Field}, {@link FieldMap @FieldMap} values,
     * {@link Header @Header}, {@link HeaderMap @HeaderMap}, {@link Path @Path},
     * {@link Query @Query}, and {@link QueryMap @QueryMap} values.
     */
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
                                                Retrofit retrofit) {
        return super.stringConverter(type,annotations,retrofit);
    }

}
