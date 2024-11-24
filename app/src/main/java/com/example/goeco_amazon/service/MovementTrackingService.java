package com.example.goeco_amazon.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.app.Service;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import com.example.goeco_amazon.R;

public class MovementTrackingService extends Service implements SensorEventListener {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MovementTrackingChannel";
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor linearAccelerationSensor;
    private float[] gravity = new float[3];
    private float[] linearAcceleration = new float[3];
    private float lastSpeed = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create notification channel if needed (for API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Movement Tracking",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        // Create notification for the foreground service
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Movement Tracking")
                .setContentText("Tracking movement in the background")
                .setSmallIcon(R.drawable.baseline_location_on_24)  // Replace with your own icon
                .build();

        // Start the service as a foreground service
        startForeground(NOTIFICATION_ID, notification);

        // Initialize SensorManager and sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // Register the sensors
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        if (linearAccelerationSensor != null) {
            sensorManager.registerListener(this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Keep the service running until explicitly stopped
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister the sensor listener to avoid memory leaks
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            linearAcceleration[0] = event.values[0];
            linearAcceleration[1] = event.values[1];
            linearAcceleration[2] = event.values[2];

            float speed = (float) Math.sqrt(
                    linearAcceleration[0] * linearAcceleration[0] +
                            linearAcceleration[1] * linearAcceleration[1] +
                            linearAcceleration[2] * linearAcceleration[2]
            );

            Log.d("MovementTrackingService", "Speed: " + speed);
            if (speed > 0.5f) {
                sendMovementUpdate("Walking/Running");
            } else {
                sendMovementUpdate("Stationary");
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }

    private void sendMovementUpdate(String movementType) {
        // Send movement type update to the activity (or database)
        Intent intent = new Intent("MovementAndLocationUpdate");
        intent.putExtra("MovementType", movementType);
        sendBroadcast(intent);
    }
}
