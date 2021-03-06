package com.example.paint.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.paint.R;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {

    private final static long TIME_TO_WELCOME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
                Intent i = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(i);
                MainActivity.this.finish();
            },TIME_TO_WELCOME);

    }
}