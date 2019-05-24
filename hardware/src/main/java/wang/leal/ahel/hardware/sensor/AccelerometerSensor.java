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
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
