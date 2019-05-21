package wang.leal.ahel.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPreferencesUtil {
    private static SharedPreferencesUtil sharedPreferenceUtil;
    private static SharedPreferences sharedPreferences;

    private final static String DEFAULT_FILE = "default_config";

    private SharedPreferencesUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(DEFAULT_FILE, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        if (sharedPreferenceUtil == null) {
            synchronized (SharedPreferencesUtil.class){
                if (sharedPreferenceUtil==null){
                    sharedPreferenceUtil = new SharedPreferencesUtil(context);
                }
            }
        }
        return sharedPreferenceUtil;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putStringSet(String key, Set<String> stringSet) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, stringSet);
        editor.commit();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }

    public long getLong(String key, Long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public float getFloat(String key, Float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public boolean has(String key) {
        return sharedPreferences.contains(key);
    }

    public boolean remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(DEFAULT_FILE, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences(Context context,String fileName){
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context){
        return context.getSharedPreferences(DEFAULT_FILE, Context.MODE_PRIVATE).edit();
    }

    public static SharedPreferences.Editor getEditor(Context context,String fileName){
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
    }

    public static void putString(Context context,String fileName,String key, String value) {
        SharedPreferences.Editor editor = getEditor(context, fileName);
        editor.putString(key, value);
        editor.commit();
    }

    public static void putStringSet(Context context,String fileName,String key, Set<String> stringSet) {
        SharedPreferences.Editor editor = getEditor(context, fileName);
        editor.putStringSet(key, stringSet);
        editor.commit();
    }

    public static void putLong(Context context,String fileName,String key, long value) {
        SharedPreferences.Editor editor = getEditor(context, fileName);
        editor.putLong(key, value);
        editor.commit();
    }

    public static void putInt(Context context,String fileName,String key, int value) {
        SharedPreferences.Editor editor = getEditor(context, fileName);
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putBoolean(Context context,String fileName,String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context, fileName);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putFloat(Context context,String fileName,String key, float value) {
        SharedPreferences.Editor editor = getEditor(context, fileName);
        editor.putFloat(key, value);
        editor.commit();
    }

    public static String getString(Context context,String fileName,String key) {
        return getString(context,fileName,key,"");
    }

    public static String getString(Context context,String fileName,String key, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context,fileName);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static boolean getBoolean(Context context,String fileName,String key) {
        return getBoolean(context,fileName,key, false);
    }

    public static boolean getBoolean(Context context,String fileName,String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context,fileName);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static int getInt(Context context,String fileName,String key) {
        return getInt(context,fileName,key,0);
    }

    public static int getInt(Context context,String fileName,String key, int defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context,fileName);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static long getLong(Context context,String fileName,String key) {
        return getLong(context,fileName,key, 0L);
    }

    public static long getLong(Context context,String fileName,String key, Long defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context,fileName);
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static float getFloat(Context context,String fileName,String key) {
        return getFloat(context,fileName,key, 0f);
    }

    public static float getFloat(Context context,String fileName,String key, Float defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context,fileName);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static boolean contains(Context context,String fileName,String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context,fileName);
        return sharedPreferences.contains(key);
    }

    public static boolean has(Context context,String fileName,String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context,fileName);
        return sharedPreferences.contains(key);
    }

    public static boolean remove(Context context,String fileName,String key) {
        SharedPreferences.Editor editor = getEditor(context,fileName);
        editor.remove(key);
        return editor.commit();
    }
}
