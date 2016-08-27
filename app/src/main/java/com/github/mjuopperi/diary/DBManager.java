package com.github.mjuopperi.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBManager {

    private DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase db;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public void insert(Date date, String title, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DATE, formatDate(date));
        contentValues.put(DBHelper.TITLE, title);
        contentValues.put(DBHelper.DESCRIPTION, description);
        db.insert(DBHelper.TABLE_NAME, null, contentValues);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DBHelper.ID, DBHelper.DATE, DBHelper.TITLE, DBHelper.DESCRIPTION, DBHelper.CREATED_AT };
        Cursor cursor = db.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id, Date date, String title, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DATE, formatDate(date));
        contentValues.put(DBHelper.TITLE, title);
        contentValues.put(DBHelper.DESCRIPTION, description);
        return db.update(DBHelper.TABLE_NAME, contentValues, DBHelper.ID + " = " + id, null);
    }

    public void delete(long id) {
        db.delete(DBHelper.TABLE_NAME, DBHelper.ID + "=" + id, null);
    }

}