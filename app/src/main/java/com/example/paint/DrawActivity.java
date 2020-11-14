package com.example.paint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class DrawActivity extends FragmentActivity {


    private int color = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);
    }




    public void setBackgroundColor(int newColor) {
        color = newColor;
        View rootView = findViewById(R.id.canvas);
        rootView.setBackgroundColor(color);
  //      pallet.addColorToPallet(newColor);
    }



    public void showColorSelector(View v){
        final ColorPicker cp = new ColorPicker(this, Color.red(color), Color.green(color), Color.blue(color));
        cp.enableAutoClose();
        cp.setCallback(this::setBackgroundColor);
        cp.show();
    }



    public void aboutClicked(View v){
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }


}