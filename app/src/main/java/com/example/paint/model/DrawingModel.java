package com.example.paint.model;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Pair;

import com.example.paint.util.PaintCanvas;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DrawingModel {

    private String id;
    private String username;
    private List<Pair<Path, PaintModel>> drawing = new LinkedList<>();
    private int backGroundColor = Color.WHITE;
    private LocalDateTime time;

    public DrawingModel(String email, PaintCanvas canvas) {
        this.username = email;
        this.drawing = canvas.getDrawing().stream().map(p -> new Pair<>(p.first,new PaintModel(p.second))).collect(Collectors.toList());
        this.backGroundColor = canvas.getBackGroundColor();
        this.time = LocalDateTime.now();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDrawing(LinkedList<Pair<Path, Paint>> drawing) {
        this.drawing = drawing.stream().map(p -> new Pair<>(p.first,new PaintModel(p.second))).collect(Collectors.toList());
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public List<Pair<Path, PaintModel>> getDrawing() {
        return drawing;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        String drawingString = "";
        for (Pair<Path, PaintModel> p : drawing)
            drawingString+= "|" + p.first.toString()+":"+p.second.toString()+"|" ;

        return "DrawingModel{" +
                "username='" + username + '\'' +
                ", drawing=" + drawingString +
                ", backGroundColor=" + backGroundColor +
                ", time=" + time +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
