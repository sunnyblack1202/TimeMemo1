package com.example.timememo1;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.util.Calendar;
import java.util.Locale;

public class TimePickDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String starttime = getArguments().getString("memoStarttime", "");


        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        
        return new TimePickerDialog(getActivity(),
                (TimePickerDialog.OnTimeSetListener)getActivity(),
                hour, minute, true);
    }
}
