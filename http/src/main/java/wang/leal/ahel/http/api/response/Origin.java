package wang.leal.ahel.http.api.response;

import androidx.annotation.NonNull;

import wang.leal.ahel.http.json.gson.GsonManager;

/**
 * 只用来识别是否直接使用原始数据转换Response
 */
public class Origin {

    @NonNull
    @Override
    public String toString() {
        return GsonManager.gson().toJson(this);
    }

}
