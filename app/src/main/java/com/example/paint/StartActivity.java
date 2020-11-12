package com.example.paint;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends Activity {

    private final static long TIME_TO_WELCOME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(StartActivity.this, DrawActivity.class);
            startActivity(i);
            StartActivity.this.finish();
        },TIME_TO_WELCOME);
    }
}