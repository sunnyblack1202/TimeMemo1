package com.example.timememo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener,
        SettimePickdialogFragment.SettimePickdialogFragmentResultListener{

    private Button btnmemoSettime;
    private Button btnmemoStarttime;
    private Button btnmemoEndingtime;

    private int settimeHour = -1;
    private int settimeMinute = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();

        String memoTitle = intent.getStringExtra("memoTitle");
        String memoSettime = intent.getStringExtra("memoSettime");
        //String memoStarttime = intent.getStringExtra("memoStarttime");
        //String memoEndingtime = intent.getStringExtra("memoEndingtime");

        TextView etmemoTitle = findViewById(R.id.etEditTitle);
        btnmemoSettime = findViewById(R.id.btnEditSettime);
        btnmemoStarttime = findViewById(R.id.btnEditStarttime);
        btnmemoEndingtime = findViewById(R.id.btnEditEndingtime);

        etmemoTitle.setText(memoTitle);
        //btnmemoSettime.setText(memoSettime);
        //tvmemoStarttime.setText(memoStarttime);
        //tvmemoEndingtime.setText(memoEndingtime);

        //戻るボタン
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //オプションメニュー
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_savedelete, menu);
        return true;
    }

    //戻るボタン
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        boolean returnVal  = true;
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else {
            returnVal = super.onOptionsItemSelected(item);
        }
        return  returnVal;
    }


    //Dialogを表示 android:onClick
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickDialogFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    //TimePickDialogFragmentでセットされた値を部品に
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String str = String.format("%02d:%02d", hourOfDay, minute);

        btnmemoStarttime.setText(str);

        SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
        try {
            //Date型に
            Date date = sdFormat.parse(str);
            //Calender型に
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);

            //計算メソッドを
            calculation(cl);

            //Date型に
            Date edate = new Date();
            edate = cl.getTime();
            //String型に
            String eStr = sdFormat.format(edate);

            btnmemoEndingtime.setText(eStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    //Dialogを表示 android:onClick
    public void showSetTimePickerDialog(View v) {
        DialogFragment newFragment = new SettimePickdialogFragment();
        newFragment.show(getSupportFragmentManager(), "SettimePicker");
    }

    //Settimeを受け取る
    public void onFragmentResult(String hour, String minute) {
        btnmemoSettime.setText(hour + ":" + minute);

        settimeHour = Integer.parseInt(hour);
        settimeMinute = Integer.parseInt(minute);
    }

    //計算
    public Calendar calculation(Calendar cl) {
        if (settimeHour != -1 || settimeMinute != -1) {
            if (settimeMinute != -1) {
                cl.add(Calendar.HOUR_OF_DAY, settimeHour);
            }
            if (settimeMinute != -1) {
                cl.add(Calendar.MINUTE, settimeMinute);
            }
        }
        return cl;
    }

}