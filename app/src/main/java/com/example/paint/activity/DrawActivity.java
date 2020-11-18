package com.example.paint.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import android.view.Window;

import androidx.fragment.app.FragmentActivity;

import com.example.paint.R;
import com.example.paint.fragment.CanvasFragment;
import com.example.paint.fragment.MenuFragment;
import com.example.paint.model.DrawingModel;
import com.example.paint.util.LimitedList;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class DrawActivity extends FragmentActivity {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor, lightSensor;
    private boolean isAccelerometerAvailable, isLightSensorAvailable;


    private CanvasFragment canvasFragment;
    private MenuFragment menuFragment;

    private DatabaseReference myDrawingsDB;
    private FirebaseUser user;

    private int color = Color.WHITE;
    private LimitedList<Integer> history = new LimitedList<Integer>(15);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);
        checkSignedUser();

        setupSensor();
        canvasFragment = (CanvasFragment) getSupportFragmentManager().findFragmentById(R.id.canvas);
        menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu);
        history.add(canvasFragment.getCanvas().getPaintColor());

        myDrawingsDB = FirebaseDatabase.getInstance().getReference().child("MyDrawings");
    }



    public void setBackgroundColor(int newColor) {
        canvasFragment.setBackgroundColor(newColor);
    }

    public CanvasFragment getCanvasFragment(){
        return canvasFragment;
    }

    public LimitedList<Integer> getHistory(){
        return history;
    }

    public void undo(View v){
        canvasFragment.getCanvas().undo();
    }

    public void redo(View v){
        canvasFragment.getCanvas().redo();
    }

    public void erase(View v){
        canvasFragment.erase();
    }


    public void saveDrawing(View v){
        DrawingModel model = new DrawingModel(user.getUid(),canvasFragment.getCanvas());
        myDrawingsDB.child(model.getUsername()).setValue(model);
        Toast.makeText(getApplicationContext(),"Drawn saved",  Toast.LENGTH_SHORT).show();
    }



    public void getDrawing(View v){
    }



    public void setPaintColor(int color){
        canvasFragment.setPaintColor(color);
        history.add(color);
        if(menuFragment != null)
            menuFragment.updateColorPalette(color);
    }

    public void showColorSelector(View v){
        int color = canvasFragment.getCanvas().getPaintColor();
        final ColorPicker cp = new ColorPicker(this, Color.red(color), Color.green(color), Color.blue(color));
        cp.enableAutoClose();
        cp.setCallback(this::setPaintColor);
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


    private void checkSignedUser() {
        // Initialize Firebase Auth
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
    }
    private void setupSensor(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!= null){
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerAvailable = true;
        }else {
            isAccelerometerAvailable = false;
            System.out.println("Accelerometer not available");
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!= null){
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            isLightSensorAvailable = true;
        }else {
            isLightSensorAvailable = false;
            System.out.println("Light sensor not available");
        }
    }

    public SensorManager getSensorManager(){
        return sensorManager;
    }

    public Sensor getLightSensor(){
        return lightSensor;
    }

    public Sensor getAccelerometerSensor(){
        return accelerometerSensor;
    }

    public boolean getIsAccelerometerAvailable(){
        return isAccelerometerAvailable;
    }
    public boolean getIsLightSensorAvailable(){

        return isLightSensorAvailable;
    }
}