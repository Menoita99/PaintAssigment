package com.example.paint.fragment;

import android.view.Gravity;
import android.widget.LinearLayout;
import  android.widget.TableRow.LayoutParams;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.paint.activity.DrawActivity;
import com.example.paint.util.LimitedList;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private DrawActivity drawActivity;
    private ArrayList<TextView> buttonList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        drawActivity = ((DrawActivity)getActivity());
        return createColorPalette();
    }


    private View createColorPalette(){
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(25,0,0,0);
        TableLayout tableLayout = new TableLayout(layout.getContext());

        LayoutParams p = new LayoutParams();
        p.rightMargin = 5;
        p.bottomMargin = 5;

        int buttonNumber = 0;
        for (int i = 0; i < 5; i++) {
            TableRow tableRow = new TableRow(getContext());
            for (int j = 0; j < 3; j++) {
                TextView button = new TextView(getContext());
                button.setPadding(20, 20, 20, 20);
                button.setLayoutParams(p);
                button.setText("" + buttonNumber);
                button.setBackgroundColor(getHistory().getOrDefault(buttonNumber, Color.WHITE));
                button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tableRow.addView(button);

                button.setOnTouchListener(new ChangeBackgroundColorListener(buttonNumber));
                buttonNumber++;
                buttonList.add(button);
            }
            tableLayout.addView(tableRow);

        }

        TextView button = new TextView(getContext());
        button.setPadding(20, 20, 20, 20);
        button.setLayoutParams(p);
        button.setText("Current color");
        button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        buttonList.add(button);

        layout.addView(tableLayout);
        layout.addView(button);

        return layout;
    }

    public void updateColorPalette(int color){
        for(TextView button : buttonList){
            if(buttonList.indexOf(button) != buttonList.size()-1)
                button.setBackgroundColor(getHistory().getOrDefault(buttonList.indexOf(button), Color.WHITE));
             else
                button.setBackgroundColor(color);
        }
    }



    private LimitedList<Integer> getHistory(){
        return drawActivity.getHistory();
    }



    private class ChangeBackgroundColorListener implements View.OnTouchListener {
        private int buttonId;

        public ChangeBackgroundColorListener(int buttonId){
            this.buttonId = buttonId;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            drawActivity.getCanvasFragment().setPaintColor(getHistory().getOrDefault(buttonId, Color.WHITE));
            buttonList.get(buttonList.size()-1).setBackgroundColor(getHistory().getOrDefault(buttonId, Color.WHITE));
            return false;
        }
    }
}