package com.example.timememo1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timegear {

    //settimeを返す
    public String settimeArrange(int settimeHour, int settimeMinute) {
        String sstr;
        switch (settimeHour) {
            case 0:
                //00:
                if (settimeMinute > 0) {
                    //00:11
                    sstr = settimeMinute + "分";
                } else {
                    //00:00
                    sstr = "0分";
                }
                break;
            default:
                //11:
                if (settimeMinute > 0) {
                    //11:11
                    sstr = settimeHour + "時間" + settimeMinute + "分";
                } else {
                    //11:00
                    sstr = settimeHour + "時間";
                }
                break;
        }

        return sstr;
    }

    //Endtimeを返す
    public String setFormatTime(String memoStarttime, int settimeHour, int settimeMinute, String endtime){
        String memoEndtime = endtime;
        SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
        try {
            //Date型に
            Date date = sdFormat.parse(memoStarttime);
            //Calender型に
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);

            //計算メソッドを
            Calendar chcl = calcTime(cl, settimeHour, settimeMinute);

            //Date型に
            Date edate = new Date();
            edate = chcl.getTime();
            //String型に
            memoEndtime = sdFormat.format(edate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return memoEndtime;
    }

    //計算
    public Calendar calcTime(Calendar cl, int settimeHour, int settimeMinute) {
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
