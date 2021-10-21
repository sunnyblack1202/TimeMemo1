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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TMDatabaseHelper _helper;
    private Cursor _cursor;

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

                Date nowDate = new Date();
                SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
                String memoStarttime = sdFormat.format(nowDate);

                fabintent.putExtra("memoStarttime", memoStarttime);
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
                TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_MINUTE,
                TMDatabaseContract.TimememoContent.COLUMN_START_TIME,
                TMDatabaseContract.TimememoContent.COLUMN_END_TIME
        };

        _cursor = db.query(
                TMDatabaseContract.TimememoContent.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        TMCursorAdapter adapter = new TMCursorAdapter(MainActivity.this, _cursor);

        ListView lvMemo = findViewById(R.id.lvMemo);

        lvMemo.setAdapter(adapter);

        lvMemo.setOnItemClickListener(new ListItemClickListener());

        lvMemo.setOnItemLongClickListener(new ListItemLongClickListener());
    }


    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            _cursor = (Cursor) parent.getItemAtPosition(position);

            int memoId = _cursor.getInt(0);
            String memoTitle = _cursor.getString(1);
            int memoSettimeHour = _cursor.getInt(2);
            int memoSettimeMinute = _cursor.getInt(3);
            String memoStarttime = _cursor.getString(4);
            String memoEndtime = _cursor.getString(5);

            Intent intent = new Intent(MainActivity.this, EditActivity.class);

            intent.putExtra("memoId", memoId);
            intent.putExtra("memoTitle", memoTitle);
            intent.putExtra("memoSettimeHour", memoSettimeHour);
            intent.putExtra("memoSettimeMinute", memoSettimeMinute);
            intent.putExtra("memoStarttime", memoStarttime);
            intent.putExtra("memoEndtime", memoEndtime);

            startActivity(intent);
        }
    }

    //長押しでダイアログを
    private class ListItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            DeleteConfirmDialogFragment dialogFragment = new DeleteConfirmDialogFragment();

            _cursor = (Cursor) parent.getItemAtPosition(position);
            int memoId = _cursor.getInt(0);
            String memoTitle = _cursor.getString(1);

            int main = -3;

            Bundle args = new Bundle();
            args.putString("memoTitle", memoTitle);
            args.putInt("memoId", memoId);

            args.putInt("activity", main);

            dialogFragment.setArguments(args);

            dialogFragment.show(getSupportFragmentManager(), "DeleteConfirmDialogFragment");
            return true;
        }
    }

    //imagebutton
    //public void startbtn_onClick(AdapterView<?> parent, View view, int position, long id) {
        //_cursor = (Cursor) parent.getItemAtPosition(position);
    //}
}