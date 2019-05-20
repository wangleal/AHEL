package wang.leal.ahel.http.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Gson manager
 * Created by wang leal on 2016/6/24.
 */
public class GsonManager {

    private static Gson defaultGson;
    private static JsonParser jsonParser = new JsonParser();

    /**
     * 自定义TypeAdapter ,null对象将被解析成空字符串
     */
    private static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
        public String read(JsonReader reader) {
            try {
                switch (reader.peek()) {
                    case NUMBER:
                        Double v = reader.nextDouble();
                        int intValue = v.intValue();
                        if (v == intValue) {//服务端返回的是int，客户端用string接收，但是gson默认会转成double型的string处理（比如1.0），这里再转成int型的string（比如1）
                            return String.valueOf(intValue);
                        }
                        long longValue = v.longValue();
                        if (v == longValue) {//服务端返回的是long，客户端用string接收，但是gson默认会转成double型的string处理（比如1.0），这里再转成long型的string（比如1）
                            return String.valueOf(longValue);
                        }
                        return String.valueOf(v);
                    case BOOLEAN:
                        Boolean bool = reader.nextBoolean();
                        return bool.toString();
                    case NULL:
                        reader.nextNull();
                        return "";//原先是返回Null，这里改为返回空字符串
                    case BEGIN_ARRAY:
                        JsonArray array = new JsonArray();
                        reader.beginArray();
                        while (reader.hasNext()) {
                            array.add(read(reader));
                        }
                        reader.endArray();
                        return array.toString();
                    case BEGIN_OBJECT:
                        JsonObject object = new JsonObject();
                        reader.beginObject();
                        while (reader.hasNext()) {
                            object.add(reader.nextName(), new JsonPrimitive(read(reader)));
                        }
                        reader.endObject();
                        return object.toString();
                }
                return reader.nextString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public void write(JsonWriter writer, String value) {
            try {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 自定义adapter，解决由于数据类型为Int,实际传过来的值为Float，导致解析出错的问题
     * 目前的解决方案为将所有Int类型当成Double解析，再强制转换为Int
     */
    private static final TypeAdapter<Number> INTEGER = new TypeAdapter<Number>() {
        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0;
            }
            try {
                double i = in.nextDouble();//当成double来读取
                return (int) i;//强制转为int
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    };

    //更新Float为空串时解析异常的问题。
    private static final TypeAdapter<Number> FLOAT = new TypeAdapter<Number>() {
        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0;
            }

            try {
                return (float) in.nextDouble();
            } catch (Exception e) {
                in.skipValue();
                return 0;
            }
        }

        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    };


    //更新Double为空串时解析异常的问题。
    private static final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>() {
        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0;
            }
            try {
                return in.nextDouble();
            } catch (Exception e) {
                in.skipValue();
                return 0;
            }
        }

        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    };

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String.class, STRING);
        gsonBuilder.registerTypeAdapter(int.class, INTEGER);
        gsonBuilder.registerTypeAdapter(Integer.class, INTEGER);
        gsonBuilder.registerTypeAdapter(float.class, FLOAT);
        gsonBuilder.registerTypeAdapter(Float.class, FLOAT);
        gsonBuilder.registerTypeAdapter(Double.class, DOUBLE);
        gsonBuilder.registerTypeAdapter(double.class, DOUBLE);

        //通过反射获取instanceCreators属性
        try {
            Class builder = gsonBuilder.getClass();
            Field f = builder.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBuilder);
            //注册数组的处理器
            gsonBuilder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        defaultGson = gsonBuilder.create();
    }

    public static Gson gson(){
        return defaultGson;
    }

    public static JsonParser jsonParser(){
        return jsonParser;
    }
}
