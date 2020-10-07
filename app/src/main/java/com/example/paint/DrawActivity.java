package com.example.paint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class DrawActivity extends Activity {

    private int color = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);
        setBackgroundColor(color);
    }

    private void setBackgroundColor(int newColor) {
        color = newColor;
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setBackgroundColor(color);
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