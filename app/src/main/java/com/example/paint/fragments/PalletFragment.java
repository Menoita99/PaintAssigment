package com.example.paint.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.fragment.app.DialogFragment;

import com.example.paint.DrawActivity;
import com.example.paint.R;
import com.example.paint.util.LimitedList;

public class PalletFragment extends DialogFragment {

    private final static int ROWS = 5;
    private final static int COLUMNS = 2;
    private final LimitedList<Integer> pallet = new LimitedList<>(ROWS*COLUMNS);
    private final LimitedList<Button> buttons = new LimitedList<>(ROWS*COLUMNS);
    private TableLayout palletTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pallet, container, false);
        palletTable = v.findViewById(R.id.pallet_table);
        initPalletTable();
        return v;
    }

    private void initPalletTable() {
        palletTable.setMinimumWidth(20*2);
        for (int i = 0; i < ROWS; i++) {
            TableRow row = new TableRow(getActivity());
            for (int j = 0; j < COLUMNS; j++) {
                Button b = new Button(getActivity());
                b.setHeight(20);
                b.setWidth(20);
                b.setBackgroundColor(Color.WHITE);
                DrawActivity activity = (DrawActivity)getActivity();
                b.setOnClickListener(view -> {
                    assert activity != null;
                    activity.setBackgroundColor(((ColorDrawable)b.getBackground()).getColor());
                });
                buttons.add(b);
            }
        }
    }

    public void addColorToPallet(int color){
        if(!pallet.contains(color)) {
            pallet.add(color);
            for (int i = 0; i < pallet.size(); i++)
                buttons.get(i).setBackgroundColor(pallet.get(i));
        }
    }

}