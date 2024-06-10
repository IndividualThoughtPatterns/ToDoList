package com.example.myapplication;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "tasks"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_NOTES = "notes";

    public static final String COLUMN_DEADLINE_DATE = "deadline_date";
    public static final String COLUMN_DEADLINE_TIME = "deadline_time";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        Log.d("req", "CREATE TABLE IF NOT EXISTS "
                + TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_NOTES + " TEXT, "
                + COLUMN_DEADLINE_DATE + " TEXT, "
                + COLUMN_DEADLINE_TIME + " TEXT"
                + ");"
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_NOTES + " TEXT, "
                + COLUMN_DEADLINE_DATE + " TEXT, "
                + COLUMN_DEADLINE_TIME + " TEXT"
                + ");"
        );
        String TAG = "MyActivity";
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
    }
}