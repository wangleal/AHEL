package wang.leal.ahel.http.api.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import wang.leal.ahel.http.json.GsonManager;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public class ResponseHelper {

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
        if (!resultObject.has(dataField)){
            throw new ConverterException("Response json doesn't have '" +dataField+ "',Consulting with server developer to add '" +dataField+ "'.");
        }
        JsonElement dataElement = resultObject.get(dataField);
        if (code == 200){
            return GsonManager.INSTANCE.gson().fromJson(dataElement,type);
        }else {
            throw new ApiException(code, message,dataElement.toString());
        }
    }

}
