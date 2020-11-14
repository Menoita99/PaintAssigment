package com.example.paint.fragment;

import android.app.Activity;
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

import com.example.paint.DrawActivity;
import com.example.paint.util.LimitedList;

public class MenuFragment extends Fragment {

    private DrawActivity drawActivity;
    LimitedList<Integer> history;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        drawActivity = ((DrawActivity)getActivity());

        return createColorPalette();
    }


    private View createColorPalette(){
        TableLayout tableLayout = new TableLayout(getContext());
        TableRow tableRow;
        TextView button;

        android.widget.TableRow.LayoutParams p = new android.widget.TableRow.LayoutParams();
        p.rightMargin = 10;
        p.bottomMargin = 10;


        history = drawActivity.getHistory();
        int buttonNumber = 0;
        for (int i = 0; i < 5; i++) {
            tableRow = new TableRow(getContext());
            for (int j = 0; j < 3; j++) {
                button = new TextView(getContext());
                button.setPadding(20, 20, 20, 20);
                tableRow.addView(button);
                button.setLayoutParams(p);
                button.setText("" + buttonNumber);

                button.setBackgroundColor(history.getOrDefault(buttonNumber, Color.WHITE));
                button.setOnTouchListener(new ChangeBackgroudColorListener(buttonNumber));
                buttonNumber++;
            }
            tableLayout.addView(tableRow);

        }

        return tableLayout;
    }



    private class ChangeBackgroudColorListener implements View.OnTouchListener {

        private int buttonId;

        public ChangeBackgroudColorListener(int buttonId){
            this.buttonId = buttonId;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            drawActivity.setBackgroundColor(history.getOrDefault(buttonId, Color.WHITE));


            return false;
        }
    }


}