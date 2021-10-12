package com.example.timememo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    }
}