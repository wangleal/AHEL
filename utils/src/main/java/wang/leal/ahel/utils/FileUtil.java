package wang.leal.ahel.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Environment.getDataDirectory() = /data
 * Environment.getDownloadCacheDirectory() = /cache
 * Environment.getExternalStorageDirectory() = /mnt/sdcard
 * Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
 * Environment.getRootDirectory() = /system
 * getPackageCodePath() = /data/app/com.my.app-1.apk
 * getPackageResourcePath() = /data/app/com.my.app-1.apk
 * getCacheDir() = /data/data/com.my.app/cache
 * getDatabasePath(“test”) = /data/data/com.my.app/databases/test
 * getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test
 * getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
 * getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
 * getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files
 * getFilesDir() = /data/data/com.my.app/files
 */
public class FileUtil {

    public static String getExternalCacheFilePath(Context context,String fileName){
        File cacheDir = context.getApplicationContext().getExternalCacheDir();
        return getFilePath(cacheDir,fileName);
    }

    public static File getExternalCacheFile(Context context,String fileName){
        File cacheDir = context.getApplicationContext().getExternalCacheDir();
        return getFile(cacheDir,fileName);
    }


    /**
     * sdcard/Android/data/package/files
     * @param context context
     * @param type The type of files directory to return. May be {@code null}
     *            for the root of the files directory or one of the following
     *            constants for a subdirectory:
     *            {@link android.os.Environment#DIRECTORY_MUSIC},
     *            {@link android.os.Environment#DIRECTORY_PODCASTS},
     *            {@link android.os.Environment#DIRECTORY_RINGTONES},
     *            {@link android.os.Environment#DIRECTORY_ALARMS},
     *            {@link android.os.Environment#DIRECTORY_NOTIFICATIONS},
     *            {@link android.os.Environment#DIRECTORY_PICTURES}, or
     *            {@link android.os.Environment#DIRECTORY_MOVIES}.
     * @param fileName file
     * @return file path
     */
    public static String getExternalFilesFilePath(Context context,String type,String fileName){
        File cacheDir = context.getApplicationContext().getExternalFilesDir(type);
        return getFilePath(cacheDir,fileName);
    }

    /**
     * sdcard/Android/data/package/files
     * @param context context
     * @param typeOrDir The type of files directory to return. May be {@code null}
     *            for the root of the files directory or one of the following
     *            constants for a subdirectory:
     *            {@link android.os.Environment#DIRECTORY_MUSIC},
     *            {@link android.os.Environment#DIRECTORY_PODCASTS},
     *            {@link android.os.Environment#DIRECTORY_RINGTONES},
     *            {@link android.os.Environment#DIRECTORY_ALARMS},
     *            {@link android.os.Environment#DIRECTORY_NOTIFICATIONS},
     *            {@link android.os.Environment#DIRECTORY_PICTURES}, or
     *            {@link android.os.Environment#DIRECTORY_MOVIES}.
     * @param fileName file
     * @return file path
     */
    public static File getExternalFilesFile(Context context,String typeOrDir,String fileName){
        File filesDir = context.getApplicationContext().getExternalFilesDir(typeOrDir);
        return getFile(filesDir,fileName);
    }

    public static File getFilesFile(Context context,String fileName){
        File filesDir = context.getApplicationContext().getFilesDir();
        return getFile(filesDir,fileName);
    }

    public static String getFilesFilePath(Context context,String fileName){
        File filesDir = context.getApplicationContext().getFilesDir();
        return getFilePath(filesDir,fileName);
    }

    public static File getSDFile(String fileName){
        return getFile(Environment.getExternalStorageDirectory(),fileName);
    }

    public static String getSDFilePath(String fileName){
        return getFilePath(Environment.getExternalStorageDirectory(),fileName);
    }

    public static String getDiskCacheDirPath(Context context) {
        File diskCache = getDiskCacheDir(context);
        if (diskCache!=null){
            return diskCache.getAbsolutePath();
        }
        return null;
    }

    public static File getDiskCacheDir(Context context) {
        File cache = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir()!=null){
                cache = context.getExternalCacheDir();
            }
        } else {
            cache = context.getCacheDir();
        }
        return cache;
    }

    private static String getFilePath(File dirFile,String fileName){
        File file = getFile(dirFile,fileName);
        if (file==null){
            return null;
        }else {
            return file.getAbsolutePath();
        }
    }

    private static File getFile(File dirFile,String fileName){
        if (dirFile==null){
            return null;
        }

        if (!dirFile.exists()){
            if (!dirFile.mkdirs()){
                return null;
            }
        }
        String filePath = dirFile.getAbsolutePath()+File.separator+fileName;
        File file = new File(filePath);
        if (!file.exists()){
            try {
                if (!file.createNewFile()){
                    return null;
                }
                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            return file;
        }
    }

}
