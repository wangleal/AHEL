package wang.leal.ahel.http.api.response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

import wang.leal.ahel.http.api.ApiHelper;
import wang.leal.ahel.http.api.exception.ApiException;
import wang.leal.ahel.http.json.GsonManager;

public class ResponseHelper {
    public static <T> T dealResult(String json, Type type) throws ApiException{
        Gson gson = GsonManager.gson();
        if (ApiHelper.result()==null){
            return gson.fromJson(json,type);
        }else {
            JsonParser jsonParser = GsonManager.jsonParser();
            JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
            int code = jsonObject.get(ApiHelper.result().codeField()).getAsInt();
            String message = jsonObject.get(ApiHelper.result().messageField()).getAsString();
            if (code==ApiHelper.successCode()){
                return gson.fromJson(jsonObject.get(ApiHelper.result().dataField()),type);
            }else {
                throw new ApiException(code, message,json);
            }
        }
    }

}
