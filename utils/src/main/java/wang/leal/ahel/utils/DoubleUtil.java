package wang.leal.ahel.utils;

import java.text.DecimalFormat;

public class DoubleUtil {

    public static String getTwoDecimal(double value){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    public static String getThreeDecimal(double value){
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(value);
    }

    public static String getDecimal(double value,String pattern){
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

}
