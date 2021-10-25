package com.example.timememo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;


public class MainActivity extends AppCompatActivity implements DeleteConfirmDialogFragment.DeleteConfirmDialogFragmentListener {

    private TMDatabaseHelper _helper;
    private Cursor _cursor;
    private TMCursorAdapter _adapter;

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
                fabintent.putExtra("memoEndtime", memoStarttime);
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

        _adapter = new TMCursorAdapter(MainActivity.this, _cursor, FLAG_REGISTER_CONTENT_OBSERVER);

        ListView lvMemo = findViewById(R.id.lvMemo);

        lvMemo.setAdapter(_adapter);

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

    public void onDialogFragmentResult() {

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

        _adapter.changeCursor(_cursor);
        _adapter.notifyDataSetChanged();
    }

    //imagebutton
    public void startbtn_onClick(AdapterView<?> parent, View view, int position, long id) {
        _cursor = (Cursor) parent.getItemAtPosition(position);
    }
}