package wang.leal.ahel.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.functions.Consumer;
import wang.leal.ahel.R;
import wang.leal.ahel.image.Image;
import wang.leal.ahel.sample.hardware.SensorActivity;
import wang.leal.ahel.sample.http.HttpActivity;
import wang.leal.ahel.sample.lifecycle.LifecycleActivity;
import wang.leal.ahel.sample.socket.SocketActivity;
import wang.leal.ahel.sample.utils.UtilsActivity;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        findViewById(R.id.bt_http).setOnClickListener(v-> startActivity(new Intent(SampleActivity.this, HttpActivity.class)));
        findViewById(R.id.bt_utils).setOnClickListener(v -> startActivity(new Intent(SampleActivity.this, UtilsActivity.class)));
        findViewById(R.id.bt_sensor).setOnClickListener(v -> startActivity(new Intent(SampleActivity.this, SensorActivity.class)));
        findViewById(R.id.bt_socket).setOnClickListener(v -> startActivity(new Intent(SampleActivity.this, SocketActivity.class)));
        findViewById(R.id.bt_lifecycle).setOnClickListener(v -> startActivity(new Intent(SampleActivity.this, LifecycleActivity.class)));
        ImageView imageView = findViewById(R.id.iv_image);
        Image.with(this,"http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg")
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher_background)
                .circle()
                .display(imageView);
//                .load()
//                .subscribe(new Consumer<Bitmap>() {
//                    @Override
//                    public void accept(Bitmap bitmap) throws Throwable {
//                        imageView.setImageBitmap(bitmap);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Throwable {
//                        throwable.printStackTrace();
//                    }
//                });
    }
}
