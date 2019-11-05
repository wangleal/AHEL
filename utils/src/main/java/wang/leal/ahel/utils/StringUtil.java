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

    /**
     * 获取字符长度，中文算一个，英文算两个
     * @param message   字符
     * @return 长度
     */
    public static int getStringLengthByC1E2(String message){
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < message.length(); i++) {
            String temp = message.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static boolean hasChinese(String message){
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < message.length(); i++) {
            String temp = message.substring(i, i + 1);
            if (temp.matches(chinese)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasEnglish(String message){
        return message.matches(".*[a-zA-z].*");
    }

    public static boolean isAllNumber(String message){
        return message.matches("[0-9]+");
    }

    public static boolean isAllEnglish(String message){
        return message.matches("[a-zA-Z]+");
    }

    public static boolean isAllChinese(String message){
        return message.matches("[\u4e00-\u9fa5]+");
    }

    public static boolean hasNumber(String message){
        return message.matches(".*[0-9].*");
    }

}
