package com.example.manager.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建课表数据表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table persons(" +
                "id integer primary key autoincrement," +
                "person_name text," +
                "phone_number text," +
                "password text," +
                "id_card text," +
                "address text)");

        db.execSQL("create table records(" +
                "id integer primary key autoincrement," +
                "person_id integer," +
                "temperature text," +
                "date datetime)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
