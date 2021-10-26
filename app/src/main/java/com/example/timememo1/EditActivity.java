package com.example.timememo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class EditActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener,
        SettimePickdialogFragment.SettimePickdialogFragmentResultListener{

    private EditText _etmemoTitle;
    private String _memoTitle;

    private int _memoId = -1;
    private String _memoStarttime;
    private String _memoEndtime;
    private String _lockSwitch;

    private Button _btnmemoSettime;
    private Button _btnmemoStarttime;
    private Button _btnmemoEndtime;
    private Button _btnNowtime;

    private int _settimeHour = -1;
    private int _settimeMinute = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Mainから
        Intent intent = getIntent();

        _memoId = intent.getIntExtra("memoId", 0);
        _memoTitle = intent.getStringExtra("memoTitle");
        _settimeHour = intent.getIntExtra("memoSettimeHour", 0);
        _settimeMinute = intent.getIntExtra("memoSettimeMinute", 0);
        _memoStarttime = intent.getStringExtra("memoStarttime");
        _memoEndtime = intent.getStringExtra("memoEndtime");

        _lockSwitch = intent.getStringExtra("lockswitch");

        _etmemoTitle = findViewById(R.id.etEditTitle);
        _btnmemoSettime = findViewById(R.id.btnEditSettime);
        _btnmemoStarttime = findViewById(R.id.btnEditStarttime);
        _btnmemoEndtime = findViewById(R.id.btnEditEndtime);
        _btnNowtime = findViewById(R.id.btnNowTime);

        String settime = _settimeHour + "時間" + _settimeMinute + "分";

        _etmemoTitle.setText(_memoTitle);
        _btnmemoSettime.setText(settime);
        _btnmemoStarttime.setText(_memoStarttime);
        _btnmemoEndtime.setText(_memoEndtime);

        //switch button
        Switch switchLock = findViewById(R.id.switchLock);

        switch (_lockSwitch) {
            case "true" :
                switchLock.setChecked(true);
                break;
            case "false":
                switchLock.setChecked(false);
                break;
            default:
                break;
        }


        switchLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchLock.setChecked(true);
                    _lockSwitch = "true";
                } else {
                    switchLock.setChecked(false);
                    _lockSwitch = "false";
                }
            }
        });

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

    //戻るボタン オプションメニュー　save delete
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        boolean returnVal  = true;
        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
            case R.id.editOptionSave:
                save();
                break;
            case R.id.editOptionDelete:
                deleteClick();
                break;
            default:
                returnVal = super.onOptionsItemSelected(item);
                break;
        }
        return  returnVal;
    }

    //保存
    public void save() {
        TMDatabaseHelper helper = new TMDatabaseHelper(EditActivity.this);

        _memoTitle = _etmemoTitle.getText().toString();

        try(SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues cv = new ContentValues();
            cv.put(TMDatabaseContract.TimememoContent.COLUMN_NAME_TITLE, _memoTitle);
            cv.put(TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_HOUR, _settimeHour);
            cv.put(TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_MINUTE, _settimeMinute);
            cv.put(TMDatabaseContract.TimememoContent.COLUMN_START_TIME, _memoStarttime);
            cv.put(TMDatabaseContract.TimememoContent.COLUMN_END_TIME, _memoEndtime);
            cv.put(TMDatabaseContract.TimememoContent.COLUMN_LOCK, _lockSwitch);

            if (_memoId == 0) {
                db.insert(TMDatabaseContract.TimememoContent.TABLE_NAME, null, cv);
            } else {
                db.update(TMDatabaseContract.TimememoContent.TABLE_NAME,
                        cv,
                        TMDatabaseContract.TimememoContent._ID + " = ?",
                        new String[] {String.valueOf(_memoId)});
            }
        }

        finish();
    }

    //削除
    public void deleteClick() {
        DeleteConfirmDialogFragment dialogFragment = new DeleteConfirmDialogFragment();

        int detail = -5;

        Bundle args = new Bundle();
        args.putString("memoTitle", _memoTitle);
        args.putInt("memoId", _memoId);

        args.putInt("activity", detail);

        dialogFragment.setArguments(args);

        dialogFragment.show(getSupportFragmentManager(), "DeleteConfirmDialogFragment");
    }

    //Dialogを表示 android:onClick
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickDialogFragment();

        Bundle args = new Bundle();
        args.putString("memoStarttime", _memoStarttime);

        newFragment.setArguments(args);

        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    //TimePickDialogFragmentでセットされた値を受け取る
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        _memoStarttime = String.format("%02d:%02d", hourOfDay, minute);

        timegear();
    }

    //Dialogを表示 android:onClick Settime
    public void showSetTimePickerDialog(View v) {
        DialogFragment newFragment = new SettimePickdialogFragment();
        newFragment.show(getSupportFragmentManager(), "SettimePicker");
    }

    //Settimeを受け取る
    public void onFragmentResult(String hour, String minute) {
        _settimeHour = Integer.parseInt(hour);
        _settimeMinute = Integer.parseInt(minute);

        String sstr;
        switch (_settimeHour) {
            case -1:
                //00:
                if (_settimeMinute > 0){
                    //00:11
                    sstr = minute + "分";
                } else {
                    //00:00
                    sstr = "0分";
                }
                break;

            case 0:
                //00:
                if (_settimeMinute > 0){
                    //00:11
                    sstr = minute + "分";
                } else {
                    //00:00
                    sstr = "0分";
                }
                break;
            default:
                //11:
                if (_settimeMinute > 0) {
                    //11:11
                    sstr = hour + "時間" + minute + "分";
                } else {
                    //11:00
                    sstr = hour + "時間";
                }
                break;
        }

        _btnmemoSettime.setText(sstr);

        timegear();
    }

    //Now
    public void nowtime_onClick(View v){
        Date nowDate = new Date();
        SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
        _memoStarttime = sdFormat.format(nowDate);

        timegear();
    }

    public void timegear () {
        _btnmemoStarttime.setText(_memoStarttime);

        SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
        try {
            //Date型に
            Date date = sdFormat.parse(_memoStarttime);
            //Calender型に
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);

            //計算メソッドを
            Calendar chcl = calculation(cl);

            //Date型に
            Date edate = new Date();
            edate = chcl.getTime();
            //String型に
            _memoEndtime = sdFormat.format(edate);

            _btnmemoEndtime.setText(_memoEndtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //計算
    public Calendar calculation(Calendar cl) {
        if (_settimeHour != -1 || _settimeMinute != -1) {
            if (_settimeMinute != -1) {
                cl.add(Calendar.HOUR_OF_DAY, _settimeHour);
            }
            if (_settimeMinute != -1) {
                cl.add(Calendar.MINUTE, _settimeMinute);
            }
        }
        return cl;
    }

}