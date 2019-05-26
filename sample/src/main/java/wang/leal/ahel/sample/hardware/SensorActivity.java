package wang.leal.ahel.sample.hardware;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import wang.leal.ahel.R;
import wang.leal.ahel.service.sensor.OrientationDetection;

public class SensorActivity extends AppCompatActivity {
    private OrientationDetection orientationDetection;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        TextView tvInfo = findViewById(R.id.tv_info);
        orientationDetection = new OrientationDetection(getApplicationContext()) {
            @Override
            public void onOrientationChanged(int orientation) {
                tvInfo.setText("orientation:"+orientation );
            }
        };
        orientationDetection.enable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orientationDetection.disable();
    }
}
