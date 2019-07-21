package wang.leal.ahel.socket.process;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
    public String url;
    public int port;
    public String message;

    public Data(){}

    public Data(String url,int port,String message){
        this.url = url;
        this.port = port;
        this.message = message;
    }

    private Data(Parcel in) {
        this.url = in.readString();
        this.port = in.readInt();
        this.message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(port);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
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
