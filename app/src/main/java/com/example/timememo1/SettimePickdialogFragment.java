package com.example.timememo1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class SettimePickdialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_settime_pickdialog, null, false);

        final NumberPicker setnp1 = (NumberPicker) view.findViewById(R.id.settimepick1);
        setnp1.setMinValue(0);
        setnp1.setMaxValue(2);

        final NumberPicker setnp2 = (NumberPicker) view.findViewById(R.id.settimepick2);
        setnp2.setMinValue(0);
        setnp2.setMaxValue(9);

        final NumberPicker setnp3 = (NumberPicker) view.findViewById(R.id.settimepick3);
        setnp3.setMinValue(0);
        setnp3.setMaxValue(9);

        final NumberPicker setnp4 = (NumberPicker) view.findViewById(R.id.settimepick4);
        setnp4.setMinValue(0);
        setnp4.setMaxValue(9);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Activity _parentActivity = getActivity();

                String str = String.format("%s%s:%s%s", setnp1, setnp2, setnp3, setnp4);
            }
        });

        builder.setView(view);
        return builder.create();
    }
}