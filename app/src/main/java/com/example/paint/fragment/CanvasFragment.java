package com.example.paint.fragment;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.example.paint.activity.DrawActivity;
import com.example.paint.util.PaintCanvas;

public class CanvasFragment extends Fragment implements SensorEventListener {


    private PaintCanvas canvas;
    private DrawActivity drawActivity;
    private float currentX, currentY, currentZ, lastX, lastY, lastZ, xDifference, yDifference, zDifference;
    private float shakeThreshHold = 5f;
    private boolean notFirstTime = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        canvas = new PaintCanvas(getContext());
        drawActivity = ((DrawActivity) getActivity());


        return canvas;
    }


    public void setBackgroundColor(int color){
        canvas.setBackgroundColor(color);

    }





    public void setPaintColor(int newColor){
        canvas.setPaintColor(newColor);
    }



    public void erase(){
        canvas.erase();
    }


    public PaintCanvas getCanvas(){
        return canvas;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            currentX = event.values[0];
            currentY = event.values[1];
            currentZ = event.values[2];

            if (notFirstTime) {
                xDifference = Math.abs(lastX - currentX);
                yDifference = Math.abs(lastY - currentY);
                zDifference = Math.abs(lastZ - currentZ);
                if ((xDifference > shakeThreshHold && yDifference > shakeThreshHold) ||
                        (xDifference > shakeThreshHold && zDifference > shakeThreshHold ||
                                (yDifference > shakeThreshHold && zDifference > shakeThreshHold))) {

                    erase();
                }
            }

            lastX = currentX;
            lastY = currentY;
            lastZ = currentZ;
            notFirstTime = true;
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT){
            WindowManager.LayoutParams layoutParams = drawActivity.getWindow().getAttributes(); // Get Params
            layoutParams.screenBrightness = event.values[0]/40000; // Set Value
            drawActivity.getWindow().setAttributes(layoutParams); // Set params
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();

        if(drawActivity.getIsAccelerometerAvailable()){
            drawActivity.getSensorManager().registerListener(this, drawActivity.getAccelerometerSensor(), SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(drawActivity.getIsLightSensorAvailable()){
            drawActivity.getSensorManager().registerListener(this, drawActivity.getLightSensor(), SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if(drawActivity.getIsAccelerometerAvailable()){
            drawActivity.getSensorManager().unregisterListener(this, drawActivity.getAccelerometerSensor());
        }
        if(drawActivity.getIsLightSensorAvailable()){
            drawActivity.getSensorManager().unregisterListener(this, drawActivity.getLightSensor());
        }
    }
}
