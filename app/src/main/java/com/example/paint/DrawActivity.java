package com.example.paint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.paint.fragment.CanvasFragment;
import com.example.paint.util.LimitedList;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class DrawActivity extends FragmentActivity {

    private  CanvasFragment canvasFragment;

    private int color = Color.WHITE;
    private LimitedList<Integer> history = new LimitedList<Integer>(15);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);
        canvasFragment = (CanvasFragment) getSupportFragmentManager().findFragmentById(R.id.canvas);
    }


    public void setBackgroundColor(int newColor) {
        canvasFragment.setBackgroundColor(newColor);
    }




    public void showColorSelector(View v){
        int color = canvasFragment.getCanvas().getPaintColor();
        final ColorPicker cp = new ColorPicker(this, Color.red(color), Color.green(color), Color.blue(color));
        cp.enableAutoClose();
        cp.setCallback(canvasFragment::setPaintColor);
        cp.show();
    }



    public void aboutClicked(View v){
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }




    public LimitedList<Integer> getHistory(){
        return history;
    }
}