package com.github.mjuopperi.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "diary.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "diaries";

    // Table columns
    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String CREATED_AT = "created_at";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID +            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DATE +          " DATE NOT NULL, " +
            TITLE +         " TEXT NOT NULL, " +
            DESCRIPTION +   " TEXT, " +
            CREATED_AT +    " DATE DEFAULT CURRENT_TIMESTAMP" +
            ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
