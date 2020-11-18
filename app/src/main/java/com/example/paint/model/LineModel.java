package com.example.paint.model;

import android.graphics.Paint;
import android.graphics.Path;
import android.util.Pair;

public class LineModel {

    private Path path;
    private Paint paint;

    public LineModel(Pair<Path, Paint> line) {
        path = line.first;
        paint = line.second;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
