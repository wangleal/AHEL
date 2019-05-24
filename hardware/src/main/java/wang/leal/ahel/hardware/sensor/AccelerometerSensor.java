package wang.leal.ahel.hardware.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerSensor {
    private SensorManager sensorManager;
    private AccelerometerSensorListener sensorListener;
    public AccelerometerSensor(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void start(){
        sensorListener = new AccelerometerSensorListener();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void release(){
        sensorManager.unregisterListener(sensorListener);
    }

    private class AccelerometerSensorListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.e("AccelerometerSensor","x:"+x+",y:"+y+",z:"+z);
            float magnitude = x * x + y * y;
            int orientation;
            // Don't trust the angle if the magnitude is small compared to the y
            // value
            if (magnitude * 4 >= z * z) {
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-y, x) * OneEightyOverPi;
                orientation = 90 - Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
