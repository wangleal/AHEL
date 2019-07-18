package wang.leal.ahel.sample;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import wang.leal.ahel.R;
import wang.leal.ahel.sample.hardware.SensorActivity;
import wang.leal.ahel.sample.http.HttpActivity;
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
    }
}
