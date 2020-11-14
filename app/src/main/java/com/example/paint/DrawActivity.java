package com.example.paint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.paint.fragment.CanvasFragment;
import com.example.paint.fragment.MenuFragment;
import com.example.paint.util.LimitedList;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class DrawActivity extends FragmentActivity {

    private CanvasFragment canvasFragment;
    private MenuFragment menuFragment;

    private int color = Color.WHITE;
    private LimitedList<Integer> history = new LimitedList<Integer>(15);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);
        canvasFragment = (CanvasFragment) getSupportFragmentManager().findFragmentById(R.id.canvas);
        menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu);
    }


    public void setBackgroundColor(int newColor) {
        canvasFragment.setBackgroundColor(newColor);
    }




    public void showColorSelector(View v){
        int color = canvasFragment.getCanvas().getPaintColor();
        final ColorPicker cp = new ColorPicker(this, Color.red(color), Color.green(color), Color.blue(color));
        cp.enableAutoClose();
        cp.setCallback(this::setPaintColor);
        cp.show();
    }

    public void setPaintColor(int color){
        canvasFragment.setPaintColor(color);
        history.add(color);
        if(menuFragment != null)
            menuFragment.updateColorPalette(color);
    }



    public void aboutClicked(View v){
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }


    public CanvasFragment getCanvasFragment(){
        return canvasFragment;
    }

    public LimitedList<Integer> getHistory(){
        return history;
    }
}