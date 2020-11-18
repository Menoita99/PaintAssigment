package com.example.paint.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;

public class PaintCanvas extends View implements View.OnTouchListener{

    private int color = Color.BLACK;
    private LinkedList<Pair<Path,Paint>> drawing = new LinkedList<>();
    private int backGroundColor = Color.WHITE;
    private DrawingListener listener;

    private LinkedList<Pair<Path,Paint>> redo = new LinkedList<>();


    public PaintCanvas(Context c){
        super(c);
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
    }

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        color = Color.BLACK;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawing.forEach(p -> canvas.drawPath(p.first,p.second));// draws the drawing
    }

    @Override
    public boolean performClick(){
        return super.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                redo.clear();
                Path path = new Path();
                Pair<Path,Paint> line = new Pair<>(path, createPaint());
                path.moveTo(eventX, eventY);// updates the path initial point
                drawing.add(line);
                if(listener!= null)
                    listener.onAdd(line);
                return true;
            case MotionEvent.ACTION_MOVE:
                drawing.getLast().first.lineTo(eventX, eventY);// makes a line to the point each time this event is fired
                if(listener!= null)
                    listener.onChange(drawing.getLast());
                break;
            case MotionEvent.ACTION_UP:// when you lift your finger
                performClick();
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

    public void erase(){
        drawing.clear();
        if(listener!= null)
            listener.onErase();
        invalidate();
    }

    public int getPaintColor(){
        return color;
    }

    public void setPaintColor(int color){
        this.color = color;
    }


    public void undo(){
        Pair<Path, Paint> poll = drawing.pollLast();

        if(poll != null) {
            redo.add(poll);
            if(listener!= null)
                listener.onRemove(poll);
        }
        invalidate();
    }

    public void redo(){
        Pair<Path, Paint> poll = redo.pollLast();
        if(poll != null) {
            drawing.add(poll);
            if(listener!= null)
                listener.onAdd(poll);
        }
        invalidate();
    }


    private Paint createPaint(){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }


    public LinkedList<Pair<Path, Paint>> getDrawing() {
        return drawing;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public LinkedList<Pair<Path, Paint>> getRedo() {
        return redo;
    }




    public interface DrawingListener{
        void onAdd(Pair<Path,Paint> line);
        void onChange(Pair<Path,Paint> line);
        void onRemove(Pair<Path,Paint> line);
        void onErase();
    }
    public DrawingListener getListener() {
        return listener;
    }

    public void setListener(DrawingListener listener) {
        this.listener = listener;
    }
}
