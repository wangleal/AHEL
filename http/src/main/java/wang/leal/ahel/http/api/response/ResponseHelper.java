package wang.leal.ahel.http.api.response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

import wang.leal.ahel.http.api.ApiService;
import wang.leal.ahel.http.api.exception.ApiException;
import wang.leal.ahel.http.api.exception.ResponseException;
import wang.leal.ahel.http.json.GsonManager;

public class ResponseHelper {
    public static <T> T dealResult(String json, Type type) throws ApiException,ResponseException{
        Gson gson = GsonManager.gson();
        if (ApiService.result()==null){
            return gson.fromJson(json,type);
        }else {
            JsonParser jsonParser = GsonManager.jsonParser();
            JsonObject jsonObject;
            try {
                jsonObject = jsonParser.parse(json).getAsJsonObject();
            }catch (Exception e){
                throw new ResponseException("Json format error:\r\n"+json);
            }

            String codeField = ApiService.result().codeField();
            if (!jsonObject.has(codeField)){
                throw new ResponseException("Response json doesn't have \'"+codeField+"\',Please call Api.initialize() to assign code field,or consulting with server developer to add \'"+codeField+"\'.");
            }
            int code;
            try {
                code = jsonObject.get(codeField).getAsInt();
            }catch (Exception e){
                throw new ResponseException(codeField+" value must be integer:"+jsonObject.get(codeField).toString());
            }

            String messageField = ApiService.result().messageField();
            if (!jsonObject.has(messageField)){
                throw new ResponseException("Response json doesn't have \'"+messageField+"\',Please call Api.initialize() to assign message field,or consulting with server developer to add \'"+messageField+"\'.");
            }
            String message;
            try {
                message = jsonObject.get(messageField).getAsString();
            }catch (Exception e){
                throw new ResponseException(messageField+" value must be string\r\n"+jsonObject.get(messageField));
            }

            String dataField = ApiService.result().dataField();
            if (!jsonObject.has(dataField)){
                throw new ResponseException("Response json doesn't have \'"+dataField+"\',Please call Api.initialize() to assign data field,or consulting with server developer to add \'"+dataField+"\'.");
            }
            String data = jsonObject.get(dataField).toString();
            if (code==ApiService.successCode()){
                return gson.fromJson(data,type);
            }else {
                throw new ApiException(code, message,data);
            }
        }
    }

}
