package com.example.paint.model;

import android.graphics.Paint;

public class PaintModel {
    private boolean antiAlias = true;
    private float strokeWidth;
    private int color;
    private Paint.Style style;
    private Paint.Join strokeJoin;

    public PaintModel(Paint p) {
        this.antiAlias = p.isAntiAlias();
        this.strokeWidth = p.getStrokeWidth();
        this.color = p.getColor();
        this.style = p.getStyle();
        this.strokeJoin = p.getStrokeJoin();
    }

    public boolean isAntiAlias() {
        return antiAlias;
    }

    public void setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Paint.Style getPrivateStyle() {
        return style;
    }

    public void setPrivateStyle(Paint.Style privateStyle) {
        this.style = privateStyle;
    }

    public Paint.Join getStrokeJoin() {
        return strokeJoin;
    }

    public void setStrokeJoin(Paint.Join strokeJoin) {
        this.strokeJoin = strokeJoin;
    }
}
