package com.example.administrator.password.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018\5\19 0019.
 */

public class Myslqdata extends SQLiteOpenHelper {


    public Myslqdata(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Table = "create table Main (" +
                "id integer primary key autoincrement," +
                "leixing text," +
                "zhanghao text,"+
                "password text)";
        db.execSQL(Create_Table);

//        String ss="create table main ( " +
//                "id integer primary key autoincrement," +
//                "leixing text," +
//                "password text," +
//                "sss text)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
