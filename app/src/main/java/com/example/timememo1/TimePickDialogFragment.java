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
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        
        return new TimePickerDialog(getActivity(),
                (TimePickerDialog.OnTimeSetListener)getActivity(),
                hour, minute, true);
    }

    //@Override
    //public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //String str = String.format(Locale.JAPAN, "%d:%d,", hourOfDay, minute);
    //}
}
