package wang.leal.ahel.utils;

import java.nio.charset.Charset;
import java.util.Arrays;

public class ByteArrayUtil {

    /**
     * 将字节数组转为十六进制字符串
     * @param bytes 字节数组
     * @return  十六进制字符串
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (byte aSrc : bytes) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] valueOf(String hexString){
        if (hexString.length() < 1)
            return null;
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length() / 2; i++) {
            int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String toISOString(byte[] bytes){
        return toCharsetString(bytes,"ISO-8859-1");
    }

    public static String toCharsetString(byte[] bytes,String charset){
        return new String(bytes,Charset.forName(charset));
    }

    /**
     * 合并字节数组
     * @param firstArray 第一个字节数组
     * @param secondArray 第二个字节数组
     * @return 合并后的字节数组
     */
    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        if (firstArray == null || secondArray == null) {
            return null;
        }
        byte[] bytes = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
        System.arraycopy(secondArray, 0, bytes, firstArray.length,
                secondArray.length);
        return bytes;
    }

    public static String subByteArray(byte[] message,int start,int end){
        byte[] slice = Arrays.copyOfRange(message, start, end);
        return new String(slice,Charset.forName("UTF-8"));
    }

    public static String subByteArray(byte[] message,int start){
        return subByteArray(message,start,message.length);
    }

}
