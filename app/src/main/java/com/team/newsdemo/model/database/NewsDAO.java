package com.team.newsdemo.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team.newsdemo.model.NewsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android_ZzT on 17/8/9.
 */

public class NewsDAO {

    private DBHelper dbHelper;

    public NewsDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insert(NewsResponse.New _new) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.ID, _new.getId());
        values.put(DBHelper.TITLE, _new.getTitle());
        values.put(DBHelper.CATEGORY, _new.getCategory());
        values.put(DBHelper.PAGE_URL, _new.getPage_url());
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
    }

    public void delete(int new_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, DBHelper.ID + "=?", new String[]{String.valueOf(new_id)});
        db.close();
    }

    public List<NewsResponse.New> getNewsList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                "*" +
                " FROM " + DBHelper.TABLE_NAME;
        ArrayList<NewsResponse.New> newsList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NewsResponse.New _new = new NewsResponse.New();
                _new.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.ID)));
                _new.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.TITLE)));
                _new.setCategory(cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY)));
                _new.setPage_url(cursor.getString(cursor.getColumnIndex(DBHelper.PAGE_URL)));
                newsList.add(_new);
            } while (cursor.moveToNext());
        } else {
            return null;
        }
        cursor.close();
        db.close();
        return newsList;
    }

    public NewsResponse.New getNewById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                "*" +
                " FROM " + DBHelper.TABLE_NAME
                + " WHERE " +
                DBHelper.ID + "=?";
        NewsResponse.New _new = new NewsResponse.New();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            do {
                _new.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.ID)));
                _new.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.TITLE)));
                _new.setCategory(cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY)));
                _new.setPage_url(cursor.getString(cursor.getColumnIndex(DBHelper.PAGE_URL)));
            } while (cursor.moveToNext());
        } else {
            return null;
        }
        cursor.close();
        db.close();
        return _new;

    }


}
