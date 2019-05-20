package wang.leal.ahel.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableUtil {

    public static String getStackTrace(Throwable e){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

}
