package wang.leal.ahel.http.api.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kotlin.Unit;
import wang.leal.ahel.http.json.GsonManager;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public class ResponseHelper {

    @SuppressWarnings("unchecked")
    public static <T> T convert(ResponseBody value,Type type)throws IOException {
        String json = value.string();
        JsonObject resultObject;
        try {
            resultObject = JsonParser.parseString(json).getAsJsonObject();
        }catch (Exception e){
            throw new ConverterException("Json format error:\r\n"+json);
        }

        String codeField = "code";
        if (!resultObject.has(codeField)){
            throw new ConverterException("Response json doesn't have '" +codeField+ "',Consulting with server developer to add '" +codeField+ "'.");
        }
        int code;
        try {
            code = resultObject.get(codeField).getAsInt();
        }catch (Exception e){
            throw new ConverterException(codeField+" value must be integer:"+resultObject.get(codeField).toString());
        }

        String messageField = "message";
        if (!resultObject.has(messageField)){
            throw new ConverterException("Response json doesn't have '" +messageField+ "',Consulting with server developer to add '" +messageField+ "'.");
        }
        String message;
        try {
            message = resultObject.get(messageField).getAsString();
        }catch (Exception e){
            throw new ConverterException(messageField+" value must be string\r\n"+resultObject.get(messageField));
        }

        String dataField = "data";
        JsonElement dataElement = null;
        try{
            dataElement = resultObject.get(dataField);
        }catch (Exception ignored){
        }

        if (code == 200){
            if (!resultObject.has(dataField)||dataElement==null){
                String nullValue;
                if (type==int.class||type==Integer.class){
                    nullValue = "0";
                }else if (type==String.class){
                    return (T) "No data field or data is null";
                }else if (type==boolean.class||type==Boolean.class){
                    nullValue = "false";
                }else if (type==byte.class||type==Byte.class){
                    nullValue = "0";
                }else if (type==char.class||type==Character.class){
                    nullValue = "0";
                }else if (type==double.class||type==Double.class){
                    nullValue = "0";
                }else if (type==float.class||type==Float.class){
                    nullValue = "0";
                }else if (type==long.class||type==Long.class){
                    nullValue = "0";
                }else if (type==short.class||type==Short.class){
                    nullValue = "0";
                }else if (type== Unit.class){
                    return (T) Unit.INSTANCE;
                }else {
                    nullValue = "{}";
                }
                return GsonManager.gson().fromJson(nullValue,type);
            }else {
                if (type==String.class){
                    return (T) dataElement.toString();
                }else if (type== Unit.class){
                    return (T) Unit.INSTANCE;
                }
                return GsonManager.gson().fromJson(dataElement,type);
            }
        }else {
            String errorData = null;
            if (dataElement!=null){
                errorData = dataElement.toString();
            }
            throw new ApiException(code, message,errorData);
        }
    }

}
