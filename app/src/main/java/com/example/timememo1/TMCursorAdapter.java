package com.example.timememo1;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TMCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public TMCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        TextView title;
        TextView settime;
        TextView starttime;
        TextView endtime;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.memo_list_item, null);

        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) view.findViewById(R.id.tvTitle);
        holder.settime = (TextView) view.findViewById(R.id.tvSettime);
        holder.starttime = (TextView) view.findViewById(R.id.tvStarttime);
        holder.endtime = (TextView) view.findViewById(R.id.tvEndtime);

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        int memoId = cursor.getInt(0);
        String memoTitle = cursor.getString(1);
        int memoSettimeHour = cursor.getInt(2);
        int memoSettimeMinute = cursor.getInt(3);
        String memoStarttime = cursor.getString(4);
        String memoEndtime = cursor.getString(5);

        //画面にセット
        holder.title.setText(memoTitle);
        holder.settime.setText(String.valueOf(memoSettimeHour) + "時間"+ String.valueOf(memoSettimeMinute) + "分");
        holder.starttime.setText(memoStarttime);
        holder.endtime.setText(memoEndtime);
    }

}