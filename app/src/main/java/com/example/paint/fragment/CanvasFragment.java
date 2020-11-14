package com.example.paint.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.paint.util.PaintCanvas;

public class CanvasFragment extends Fragment {

    private PaintCanvas canvas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        canvas = new PaintCanvas(getContext());
        return canvas;
    }


    public void setBackgroundColor(int color){
        canvas.setBackgroundColor(color);

    }


    public void setPaintColor(int newColor){
        canvas.getPaint().setColor(newColor);
    }

    public void erase(){
        canvas.erase();
    }


    public PaintCanvas getCanvas(){
        return canvas;
    }
}
