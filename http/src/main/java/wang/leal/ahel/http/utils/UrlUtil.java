package wang.leal.ahel.http.utils;

import java.util.HashMap;
import java.util.Map;

public class UrlUtil {
    private static final Map<String,String> urlMap = new HashMap<>();
    public static String getUrlByKey(String key) {
        String url = key;
        if ("path".equals(key)){
            url = "http://www.baidu.com/{a}/";
        }
        if ("chained".equals(key)){
            url = "http://www.baidu.com/";
        }
        return url;
    }
}
