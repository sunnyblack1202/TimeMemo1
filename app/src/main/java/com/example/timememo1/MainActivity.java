package com.example.timememo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private TMDatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //追加ボタン
        FloatingActionButton fabNew = findViewById(R.id.fabNew);
        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fabintent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(fabintent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        selectDb();
    }

    public void selectDb() {
        _helper = new TMDatabaseHelper(MainActivity.this);

        SQLiteDatabase db = _helper.getWritableDatabase();

        String[] projection = {
                TMDatabaseContract.TimememoContent._ID,
                TMDatabaseContract.TimememoContent.COLUMN_NAME_TITLE,
                TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_HOUR,
                //TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_MINUTE,
                TMDatabaseContract.TimememoContent.COLUMN_START_TIME,
                TMDatabaseContract.TimememoContent.COLUMN_END_TIME
        };

        Cursor cursor = db.query(
                TMDatabaseContract.TimememoContent.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        //String setTime =
                //String.valueOf(TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_HOUR) + "時間"+
                //String.valueOf(TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_MINUTE) + "分";
        String[] FROM = {
                TMDatabaseContract.TimememoContent.COLUMN_NAME_TITLE,
                TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_HOUR,
                TMDatabaseContract.TimememoContent.COLUMN_START_TIME,
                TMDatabaseContract.TimememoContent.COLUMN_END_TIME};
        int[] TO = {R.id.tvTitle, R.id.tvSettime, R.id.tvStarttime, R.id.tvEndingtime};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,
                R.layout.memo_list_item, cursor, FROM, TO, 0);

        ListView lvMemo = findViewById(R.id.lvMemo);

        lvMemo.setAdapter(adapter);

        lvMemo.setOnItemClickListener(new LIstItemClickListener());
    }


    private class LIstItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);

            String memoTitle = item.get("title");
            String memoSettime = item.get("settime");
            String memoStarttime = item.get("starttime");
            String memoEndingtime = item.get("endingtime");

            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("memoTitle", memoTitle);
            intent.putExtra("memoSettime", memoSettime);
            intent.putExtra("memoStarttime", memoStarttime);
            intent.putExtra("memoEndingtime", memoEndingtime);

            startActivity(intent);
        }
    }
}