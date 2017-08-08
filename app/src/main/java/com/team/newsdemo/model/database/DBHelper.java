package com.team.newsdemo.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Android_ZzT on 17/8/9.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "news_demo.db";

    public static final String TABLE_NAME = "news";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CATEGORY = "category";
    public static final String PAGE_URL = "page_url";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SQL = "CREATE TABLE "+ TABLE_NAME +"("
                +ID+" INTEGER PRIMARY KEY, "
                +TITLE+" TEXT, "
                +CATEGORY+" TEXT, "
                +PAGE_URL+" TEXT)";
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
