package com.example.pillsnotify;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String NAME = "pills list";
    private static final int VERSION = 1;

    public static final String TABLE_NAME = "pills";

    public static final String COLUMN_TITLE = "tittle";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_INTERVAL = "interval";
    public static final String COLUMN_AMOUNT_TIME = "amount";


    @RequiresApi(api = Build.VERSION_CODES.P)
    public MyOpenHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_START + " TEXT, "
                + COLUMN_INTERVAL + " INTRGER, "
                + COLUMN_AMOUNT_TIME + " REAL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
