package com.example.paint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.paint.fragments.CanvasFragment;
import com.example.paint.fragments.PalletFragment;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class DrawActivity extends FragmentActivity {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListner;
    private float maxValue;

    private int color = Color.WHITE;

    private PalletFragment pallet = new PalletFragment();
    private CanvasFragment canvas = new CanvasFragment();

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListner, lightSensor, sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);
        setBackgroundColor(color);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor==null){
            Toast.makeText(this,"Device has no Light sensor :(", Toast.LENGTH_SHORT).show();
        } else {
            maxValue = lightSensor.getMaximumRange();

            lightEventListner = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float value = event.values[0];
                    int newValue = (int) (255f * value / maxValue);
                    setBackgroundColor(Color.rgb(newValue,newValue,newValue));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
        }



        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            canvas.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, canvas, "Canvas").commit();
        }

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_canvas, new CanvasFragment());
//        if( getSupportFragmentManager().findFragmentById(R.id.pallet) != null)
//            transaction.replace(R.id.fragment_pallet, pallet);
//        transaction.commit();
    }

    public void setBackgroundColor(int newColor) {
        color = newColor;
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setBackgroundColor(color);
    }

    public void changeColorClicked(View v){
        final ColorPicker cp = new ColorPicker(this, Color.red(color), Color.green(color), Color.blue(color));
        cp.enableAutoClose();
        cp.setCallback(this::setBackgroundColor);
        cp.show();
    }

    public void aboutClicked(View v){
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }

    public void mapsClicked(View v){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    public void changeFragmentClicked(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        CanvasFragment canvasFragment = (CanvasFragment)getSupportFragmentManager().findFragmentByTag("Canvas");
        PalletFragment palletFragment = (PalletFragment)getSupportFragmentManager().findFragmentByTag("Pallet");
        if (canvasFragment != null && canvasFragment.isVisible()) {
            transaction.replace(R.id.fragment_container, pallet, "Pallet");
        } else if (palletFragment != null && palletFragment.isVisible()) {
            transaction.replace(R.id.fragment_container, canvas, "Canvas");
        }
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}