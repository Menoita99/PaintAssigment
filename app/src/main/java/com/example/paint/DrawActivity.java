package com.example.paint;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.paint.fragment.CanvasFragment;
import com.example.paint.fragment.PalletFragment;
import com.example.paint.util.LimitedList;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.util.ArrayList;

public class DrawActivity extends AppCompatActivity {


    private int color = Color.WHITE;
    private PalletFragment pallet = new PalletFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);
        setBackgroundColor(color);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.canvas, new CanvasFragment());
        if( getSupportFragmentManager().findFragmentById(R.id.pallet) != null)
            transaction.replace(R.id.pallet, pallet);
        transaction.commit();
    }




    public void setBackgroundColor(int newColor) {
        color = newColor;
        View rootView = findViewById(R.id.canvas);
        rootView.setBackgroundColor(color);
        pallet.addColorToPallet(newColor);
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