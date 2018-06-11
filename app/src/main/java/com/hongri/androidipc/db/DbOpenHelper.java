package com.hongri.androidipc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author zhongyao
 * @date 2018/6/11
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";

    public static final String DB_NAME = "library";
    public static final int DB_VERSION = 1;

    private String BOOK_SQL;
    private String USER_SQL;

    public DbOpenHelper(Context context, String name,
                        CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);

        BOOK_SQL = "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT,"
            + "page INT)";
        USER_SQL = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT,"
            + "sex INT)";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BOOK_SQL);
        db.execSQL(USER_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
