package com.example.timememo1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TMDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "timememo.db";
    private static final int DATABASE_VERSION = 2;

    private static String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TMDatabaseContract.TimememoContent.TABLE_NAME + " (" +
                    TMDatabaseContract.TimememoContent._ID + " INTEGER PRIMARY KEY," +
                    TMDatabaseContract.TimememoContent.COLUMN_NAME_TITLE + " TEXT," +
                    TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_HOUR + " INTEGER," +
                    TMDatabaseContract.TimememoContent.COLUMN_SET_TIME_MINUTE + " INTEGER," +
                    TMDatabaseContract.TimememoContent.COLUMN_START_TIME + " TEXT," +
                    TMDatabaseContract.TimememoContent.COLUMN_END_TIME + " TEXT," +
                    TMDatabaseContract.TimememoContent.COLUMN_LOCK + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TMDatabaseContract.TimememoContent.TABLE_NAME;

    public TMDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
