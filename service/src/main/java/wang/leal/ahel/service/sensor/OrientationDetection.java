package wang.leal.ahel.service.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 *  Helper class for receiving notifications from the SensorManager when
 *  the orientation of the device has changed.
 *  Reference {@link android.view.OrientationEventListener}.
 */
public abstract class OrientationDetection {

    private static final String TAG = "OrientationDetection";
    private int mOrientation = ORIENTATION_UNKNOWN;
    private final SensorManager mSensorManager;
    private boolean mEnabled = false;
    private final int mRate;
    private final Sensor mSensor;
    private SensorEventListener mSensorEventListener;

    /**
     * Returned from onOrientationChanged when the device orientation cannot be determined
     * (typically when the device is in a close to flat position).
     *
     *  @see #onOrientationChanged
     */
    public static final int ORIENTATION_UNKNOWN = -1;

    /**
     * Creates a new OrientationEventListener.
     *
     * @param context for the OrientationEventListener.
     */
    public OrientationDetection(Context context) {
        this(context, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Creates a new OrientationEventListener.
     *
     * @param context for the OrientationEventListener.
     * @param rate at which sensor events are processed (see also
     * {@link android.hardware.SensorManager SensorManager}). Use the default
     * value of {@link android.hardware.SensorManager#SENSOR_DELAY_NORMAL
     * SENSOR_DELAY_NORMAL} for simple screen orientation change detection.
     */
    public OrientationDetection(Context context, int rate) {
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mRate = rate;
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mSensor != null) {
            // Create listener only if sensors do exist
            mSensorEventListener = new SensorEventListenerImpl();
        }
    }

    /**
     * Enables the OrientationEventListener so it will monitor the sensor and call
     * {@link #onOrientationChanged} when the device orientation changes.
     */
    public void enable() {
        if (mSensor == null) {
            return;
        }
        if (!mEnabled) {
            mSensorManager.registerListener(mSensorEventListener, mSensor, mRate);
            mEnabled = true;
        }
    }

    /**
     * Disables the OrientationEventListener.
     */
    public void disable() {
        if (mSensor == null) {
            Log.w(TAG, "Cannot detect sensors. Invalid disable");
            return;
        }
        if (mEnabled) {
            mSensorManager.unregisterListener(mSensorEventListener);
            mEnabled = false;
        }
    }

    class SensorEventListenerImpl implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X*X + Y*Y;
            // Don't trust the angle if the magnitude is small compared to the y value
            if (magnitude * 4 >= Z*Z) {
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float)Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int)Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            if (orientation != mOrientation) {
                mOrientation = orientation;
                onOrientationChanged(orientation);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    /*
     * Returns true if sensor is enabled and false otherwise
     */
    public boolean canDetectOrientation() {
        return mSensor != null;
    }

    /**
     * Called when the orientation of the device has changed.
     * orientation parameter is in degrees, ranging from 0 to 359.
     * orientation is 0 degrees when the device is oriented in its natural position,
     * 90 degrees when its left side is at the top, 180 degrees when it is upside down,
     * and 270 degrees when its right side is to the top.
     * {@link #ORIENTATION_UNKNOWN} is returned when the device is close to flat
     * and the orientation cannot be determined.
     *
     * @param orientation The new orientation of the device.
     *
     *  @see #ORIENTATION_UNKNOWN
     */
    public abstract void onOrientationChanged(int orientation);

}
