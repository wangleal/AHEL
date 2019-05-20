package wang.leal.ahel.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;

public class ParcelUtil {
    public static byte[] marshall(Parcelable parcelable) {
        Parcel parcel = Parcel.obtain();
        parcel.setDataPosition(0);
        parcelable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return parcel;
    }

    public static <T extends Parcelable> T unmarshall(byte[] bytes,Class<T> clazz) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        try {
            Field creatorField = clazz.getDeclaredField("CREATOR");
            Parcelable.Creator<T> creator = (Parcelable.Creator<T>) creatorField.get(clazz);
            T result = creator.createFromParcel(parcel);
            parcel.recycle();
            return result;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
