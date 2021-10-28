package com.example.timememo1;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


public class TMCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    //コンストラクタ
    public TMCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        TextView title;
        TextView settime;
        TextView starttime;
        TextView endtime;
        ImageButton startButton;
    }

    //新しいViewを作る
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.memo_list_item, null);

        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) view.findViewById(R.id.tvTitle);
        holder.settime = (TextView) view.findViewById(R.id.tvSettime);
        holder.starttime = (TextView) view.findViewById(R.id.tvStarttime);
        holder.endtime = (TextView) view.findViewById(R.id.tvEndtime);
        holder.startButton = (ImageButton) view.findViewById(R.id.startButton);

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        //Cursorからのデータ取り出し
        int memoId = cursor.getInt(0);
        String memoTitle = cursor.getString(1);
        int memoSettimeHour = cursor.getInt(2);
        int memoSettimeMinute = cursor.getInt(3);
        String memoStarttime = cursor.getString(4);
        String memoEndtime = cursor.getString(5);


        String sstr;
        switch (memoSettimeHour) {
            case 0:
                //00:
                if (memoSettimeMinute > 0){
                    //00:11
                    sstr = String.valueOf(memoSettimeMinute) + "分";
                } else {
                    //00:00
                    sstr = "0分";
                }
                break;
            default:
                //11:
                if (memoSettimeMinute > 0) {
                    //11:11
                    sstr = String.valueOf(memoSettimeHour) + "時間" + String.valueOf(memoSettimeMinute) + "分";
                } else {
                    //11:00
                    sstr = String.valueOf(memoSettimeHour) + "時間";
                }
                break;
        }

        //画面にセット
        holder.title.setText(memoTitle);
        holder.settime.setText(sstr);
        holder.starttime.setText(memoStarttime);
        holder.endtime.setText(memoEndtime);

        ImageButton startButton = (ImageButton) view.findViewById(R.id.startButton);
        startButton.setTag(cursor.getPosition());
    }

}
