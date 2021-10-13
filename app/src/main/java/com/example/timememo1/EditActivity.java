package com.example.timememo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();

        String memoTitle = intent.getStringExtra("memoTitle");
        String memoSettime = intent.getStringExtra("memoSettime");
        String memoStarttime = intent.getStringExtra("memoStarttime");
        String memoEndingtime = intent.getStringExtra("memoEndingtime");

        TextView etmemoTitle = findViewById(R.id.etEditTitle);
        TextView etmemoSettime = findViewById(R.id.etEditSettime);
        TextView etmemoStarttime = findViewById(R.id.etEditStarttime);
        TextView etmemoEndingtime = findViewById(R.id.etEditEndingtime);

        etmemoTitle.setText(memoTitle);
        etmemoSettime.setText(memoSettime);
        etmemoStarttime.setText(memoStarttime);
        etmemoEndingtime.setText(memoEndingtime);

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
}