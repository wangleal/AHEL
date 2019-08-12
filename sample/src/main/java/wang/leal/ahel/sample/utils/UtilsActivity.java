package wang.leal.ahel.sample.utils;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import wang.leal.ahel.utils.FileUtil;
import wang.leal.ahel.utils.ParcelUtil;

public class UtilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testParcel();
    }

    private void testParcel(){
        Data data = new Data("1","2");
        byte[] abc = ParcelUtil.marshall(data);
        Data dataRead = ParcelUtil.unmarshall(abc,Data.class);
        Log.e("Utils","name:"+dataRead.name+",age:"+dataRead.age);
        FileUtil.getExternalFile(getApplicationContext(),"test","a.txt");
    }
}
