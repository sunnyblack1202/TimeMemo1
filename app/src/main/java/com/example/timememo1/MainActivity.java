package com.example.timememo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String[] from = {"title", "settime", "starttime", "endingtime"};
    private static final int[] to = {R.id.tvTitle, R.id.tvSettime, R.id.tvStarttime, R.id.tvEndingtime};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvMemo = findViewById(R.id.lvMemo);

        List<Map<String, String>> _memoList = createMemoList();

        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, _memoList,
                R.layout.memo_list_item, from, to);

        lvMemo.setAdapter(adapter);
    }

    private List<Map<String, String>> createMemoList() {
        List<Map<String, String>> memoList = new ArrayList<>();
        Map<String, String> memo = new HashMap<>();
        memo.put("title", "8時間ダイエット");
        memo.put("settime", "8時間");
        memo.put("starttime", "08:30");
        memo.put("endingtime", "16:30");
        memoList.add(memo);

        memo = new HashMap<>();
        memo.put("title", "MVスミン");
        memo.put("settime", "30分");
        memo.put("starttime", "13:40");
        memo.put("endingtime", "14:10");
        memoList.add(memo);

        memo = new HashMap<>();
        memo.put("title", "食後");
        memo.put("settime", "30分");
        memo.put("starttime", "16:20");
        memo.put("endingtime", "16:50");
        memoList.add(memo);

        return memoList;
    }
}