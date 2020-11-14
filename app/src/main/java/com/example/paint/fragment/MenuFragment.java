package com.example.paint.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.paint.DrawActivity;
import com.example.paint.util.LimitedList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private DrawActivity drawActivity;
    private LimitedList<Integer> history;
    private ArrayList<TextView> buttonList = new ArrayList<TextView>();

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

                button.setOnTouchListener(new ChangeBackgroundColorListener(buttonNumber));
                buttonNumber++;
                buttonList.add(button);
            }
            tableLayout.addView(tableRow);

        }

        tableRow = new TableRow(getContext());
        button = new TextView(getContext());
        button.setPadding(20, 20, 20, 20);
        tableRow.addView(button);
        button.setLayoutParams(p);
        button.setText("C");
        buttonList.add(button);

        tableLayout.addView(tableRow);

        return tableLayout;
    }

    public void updateColorPalette(int color){
        for(TextView button : buttonList){
            if(buttonList.indexOf(button) != 15) {
                button.setBackgroundColor(history.getOrDefault(buttonList.indexOf(button), Color.WHITE));
            } else{
                button.setBackgroundColor(color);
            }
        }
    }






    private class ChangeBackgroundColorListener implements View.OnTouchListener {

        private int buttonId;

        public ChangeBackgroundColorListener(int buttonId){
            this.buttonId = buttonId;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            drawActivity.getCanvasFragment().setPaintColor(history.getOrDefault(buttonId, Color.WHITE));

            return false;
        }
    }


}