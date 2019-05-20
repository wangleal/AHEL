package wang.leal.ahel.sample.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    public String name;
    public String age;

    public Data(String name,String age){
        this.name = name;
        this.age = age;
    }

    private Data(Parcel in) {
        name = in.readString();
        age = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
