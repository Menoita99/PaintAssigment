package com.example.paint.activity;

import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;


import com.example.paint.model.LineModel;
import com.example.paint.util.PaintCanvas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SharedCanvasActivity extends AppCompatActivity {

    private PaintCanvas canvas;
    private DatabaseReference sharedDrawingDB;
    private DatabaseReference sharedCanvas;
    private String userId;

    @Override
    public void onCreate( Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        userId = getIntent().getStringExtra("userId");
        canvas = new PaintCanvas(getApplicationContext());
        sharedDrawingDB= FirebaseDatabase.getInstance().getReference().child("SharedDrawing");
        sharedDrawingDB.addValueEventListener(changeListener());
        sharedCanvas = sharedDrawingDB.child("sharedCanvas");
        canvas.setListener(getDrawingListener());
        setContentView(canvas);
    }

    private PaintCanvas.DrawingListener getDrawingListener() {
        return new PaintCanvas.DrawingListener() {
            private int lineId = 0;

            @Override
            public void onAdd(Pair<Path, Paint> line) {
                lineId++;
                sharedCanvas.child(userId+lineId).setValue(new LineModel(line));
            }

            @Override
            public void onChange(Pair<Path, Paint> line) {
                sharedCanvas.child(userId+lineId).setValue(new LineModel(line));
            }

            @Override
            public void onRemove(Pair<Path, Paint> line) {
                sharedCanvas.child(userId+lineId).removeValue();
            }

            @Override
            public void onErase() {
                sharedCanvas.removeValue();
            }
        };
    }


    private ValueEventListener changeListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                LineModel m = snapshot.getValue(LineModel.class);
                System.out.println(m);
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        };
    }
}
