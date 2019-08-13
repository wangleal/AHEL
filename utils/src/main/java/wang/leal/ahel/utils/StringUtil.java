package wang.leal.ahel.utils;

import android.text.TextUtils;

import java.nio.charset.Charset;

public class StringUtil {

    public static int getStringByteLength(String message){
        if (TextUtils.isEmpty(message)){
            return 0;
        }else {
            return message.getBytes(Charset.forName("UTF-8")).length;
        }
    }
}
