package com.example.timememo1;

import android.provider.BaseColumns;

public class TMDatabaseContract {

    /* Inner class that defines the table contents */
    public static abstract class TimememoContent implements BaseColumns {
        public static final String TABLE_NAME = "timememos";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_SET_TIME_HOUR = "settimehour";
        public static final String COLUMN_SET_TIME_MINUTE = "settimeminute";
        public static final String COLUMN_START_TIME = "starttime";
        public static final String COLUMN_END_TIME = "endtime";
        public static final String COLUMN_LOCK = "lock";
    }
}
