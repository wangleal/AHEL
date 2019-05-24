package wang.leal.ahel.sample.hardware;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import wang.leal.ahel.R;
import wang.leal.ahel.hardware.sensor.AccelerometerSensor;

public class SensorActivity extends AppCompatActivity {
    private AccelerometerSensor sensor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        TextView tvInfo = findViewById(R.id.tv_info);
        sensor = new AccelerometerSensor(getApplicationContext());
        sensor.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensor.release();
    }
}
