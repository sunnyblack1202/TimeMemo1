package com.example.timememo1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class SettimePickdialogFragment extends DialogFragment {

    //ボタンが押された時に呼ぶメソッドを持つインターフェース
    public interface SettimePickdialogFragmentResultListener {
        void onFragmentResult(int hour, int minute);
    }
    //フィールド
    private SettimePickdialogFragmentResultListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //contextをlistenerに代入
        if (context instanceof SettimePickdialogFragmentResultListener) {
            listener = (SettimePickdialogFragmentResultListener) context;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int settimeHour = getArguments().getInt("settimeHour", 0);
        int settimeMinute = getArguments().getInt("settimeMinute", 0);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_settime_pickdialog, null, false);

        final NumberPicker setnp1 = view.findViewById(R.id.settimepick1);
        setnp1.setMinValue(0);
        setnp1.setMaxValue(2);

        final NumberPicker setnp2 = view.findViewById(R.id.settimepick2);
        setnp2.setMinValue(0);
        setnp2.setMaxValue(9);

        final NumberPicker setnp3 = view.findViewById(R.id.settimepick3);
        setnp3.setMinValue(0);
        setnp3.setMaxValue(5);

        final NumberPicker setnp4 = view.findViewById(R.id.settimepick4);
        setnp4.setMinValue(0);
        setnp4.setMaxValue(9);

        //settimeHourを割り算と余剰で分割してsetValue
        int settimeHour10place = settimeHour / 10;
        setnp1.setValue(settimeHour10place);
        int settimeHour1place = settimeHour % 10;
        setnp2.setValue(settimeHour1place);
        //同様にsettimeMinuteを
        int settimeMinute10place = settimeMinute / 10;
        setnp3.setValue(settimeMinute10place);
        int settimeMinute1place = settimeMinute % 10;
        setnp4.setValue(settimeMinute1place);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Activity _parentActivity = getActivity();

                String np1 = String.valueOf(setnp1.getValue());
                String np2 = String.valueOf(setnp2.getValue());
                String np3 = String.valueOf(setnp3.getValue());
                String np4 = String.valueOf(setnp4.getValue());

                String setHour = np1 + np2;
                String setMinute = np3 + np4;

                int hour = Integer.parseInt(setHour);
                int minute = Integer.parseInt(setMinute);

                //listenerのメソッドを呼ぶ
                listener.onFragmentResult(hour, minute);
            }
        });

        builder.setView(view);
        return builder.create();
    }
}