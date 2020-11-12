package com.example.paint.fragments;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.paint.PaintCanvas;
import com.example.paint.util.GestureListener;
import com.example.paint.util.SensorActivity;


public class CanvasFragment extends Fragment {

    public CanvasFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GestureListener mGestureListener = new GestureListener();
        SensorActivity shakeActivity = new SensorActivity();
        GestureDetector mGestureDetector = new GestureDetector(getActivity().getApplicationContext(), mGestureListener);

        mGestureDetector.setIsLongpressEnabled(true);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);

        PaintCanvas paintCanvas = new PaintCanvas(getActivity().getApplicationContext(), null, mGestureDetector);

        mGestureListener.setCanvas(paintCanvas);
        shakeActivity.setCanvas(paintCanvas);

        return paintCanvas;
    }




}