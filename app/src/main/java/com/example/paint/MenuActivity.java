package com.example.paint;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.paint.fragment.MenuFragment;

public class MenuActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // menu in-line with the canvas so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the menuFragment fragment.

            // create fragment
            MenuFragment menuFragment = new MenuFragment();

            // get and set the position input by user (i.e., "index")
            // which is the construction arguments for this fragment
            menuFragment.setArguments(getIntent().getExtras());

            //set the root view to this new fragment view
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, menuFragment).commit();
        }
    }
}
