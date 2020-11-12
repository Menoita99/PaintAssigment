package com.example.paint.util;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.Nullable;

import com.example.paint.PaintCanvas;

public class SensorActivity extends  Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean sensorAvailable, notFirst=false;
    private float currentX, currentY, currentZ, lastX, lastY, lastZ;
    float xDifference, yDifference, zDifference, shakeThreshold = 2f;
    private Vibrator vibrator /*= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE)*/;
    private PaintCanvas canvas;

    public void setCanvas(PaintCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null) {
            accelerometerSensor =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorAvailable=true;
        } else {sensorAvailable=false;}
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        currentX = event.values[0];
        currentY = event.values[1];
        currentZ = event.values[2];

        if (notFirst){
            xDifference =Math.abs(lastX - currentX);
            yDifference =Math.abs(lastY - currentY);
            zDifference =Math.abs(lastZ - currentZ);

            if ((xDifference > shakeThreshold) || (yDifference > shakeThreshold)
                    || (zDifference > shakeThreshold)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    canvas.erase();
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(200);
                }
            }


        } else {
            notFirst=true;
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(sensorAvailable)
            sensorManager.registerListener(this, accelerometerSensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(sensorAvailable)
            sensorManager.unregisterListener(this);
    }
}
