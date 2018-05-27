package com.example.administrator.password.Dao;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.Encryption.AEShelper;
import com.example.administrator.password.Sql.Myslqdata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\5\19 0019.
 */

public class Maindao {
    private static String password = "plani";
    private static Myslqdata myslqdata;
    private static SQLiteDatabase sqLiteDatabase;
    private static Maindao maindao;
    private static Context context;

    public Maindao(Context context) {
        this.context = context;
    }

    //单例模式 虽然没用在这里
    public static Maindao getInstance(Context context) {
        if (maindao == null) {
            maindao = new Maindao(context);
        }
        return maindao;
    }

    // 增加数据 用insert 方法
    public static long Main_insert(Main_data main_data) {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        String leixing = AEShelper.encrypt(main_data.getLeixing(), password);
        String zhanghao = AEShelper.encrypt(main_data.getZhanghao(), password);
        String pass = AEShelper.encrypt(main_data.getPassword(), password);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("leixing", leixing);
        contentValues.put("zhanghao", zhanghao);
        contentValues.put("password", pass);
        long result=sqLiteDatabase.insert("Main", null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    //增加数据 用sql语句
    public static void Main_insertSql(String leixing, String zhanghao, String passwordshu) {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        String leixing1 = AEShelper.encrypt(leixing, password);
        String zhanghao1 = AEShelper.encrypt(zhanghao, password);
        String pass = AEShelper.encrypt(passwordshu, password);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        String sql = "insert into Main(leixing,zhanghao,password) values ('" + leixing1 + "','" + zhanghao1 + "','" + pass + "')";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    //删除数据
    public static void Main_del(String src, String key) {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        sqLiteDatabase.delete("Main", key + "=?", new String[]{src});
    }

    //删除数据 用sql
    public static void Main_delSql(String src, String key) {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        if (!key.equals("id")) {
            src = AEShelper.decrypt(src, password);

        }
        String sql = "delete from Main where " + key + "=" + src;
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    //更新数据
    public static void Main_update(String src, String key, String where, String wherearg) {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        String content = AEShelper.encrypt(src, password);
        ContentValues contentValues = new ContentValues();
        contentValues.put(key, content);
        sqLiteDatabase.update("Main", contentValues, where + "=?", new String[]{wherearg});
        sqLiteDatabase.close();
    }

    //更新数据用sql
    public static void Main_updateSql(String src, String key, String where, String wherearg) {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        String sql = "update Main set " + key + "=" + src + "where " + where + "=" + wherearg;
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    //查询所有数据
    public static List<Main_data> Main_queryall() {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Main", null, null, null, null, null, null);
        List<Main_data> datas = new ArrayList<>();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                System.out.println("数据行 主键  id   "+id);
                String leixing = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("leixing")), password);
                System.out.println("leixing"+leixing);
                String zhanghao = AEShelper.decrypt(cursor.getString(2), password);
                System.out.println("zhanghao"+zhanghao);
                String pass = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("password")), password);
//                System.out.println("pass"+pass);
//                for (int j=0;j<cursor.getColumnCount();j++){
//                    System.out.println("列名:"+cursor.getString(j));
//                }
                Main_data main_data = new Main_data(leixing, zhanghao, pass);
                main_data.setId(id);
                System.out.println("Main_data的数据"+main_data.toString());
                datas.add(main_data);
            }
        }
        return datas;
    }

    //查询所有数据用sql
    public static List<Main_data> Main_queryallSql() {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        String sql = "select * from Main ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        List<Main_data> datas = new ArrayList<>();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String leixing = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("leixing")), password);
                String zhanghao = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("zhanghao")), password);
                String pass = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("password")), password);
                Main_data main_data = new Main_data(leixing, zhanghao, pass);
                main_data.setId(id);
                datas.add(main_data);
            }
        }
        return datas;
    }

    //查询特定的数据
    public static Main_data Main_query(int id) {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Main", null, "id", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.getCount() == 1) {
            int id1 = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String leixing = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("leixing")), password);
            String zhanghao = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("zhanghao")), password);
            String pass = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("password")), password);
            Main_data main_data = new Main_data(leixing, zhanghao, pass);
            main_data.setId(id1);
            return main_data;
        } else
            return null;
    }

    //查询特定的数据用sql
    public static Main_data Main_querySql(int id) {
        myslqdata = new Myslqdata(context, "shuju", null, 1);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        String sql = "select * from Main where id=?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.getCount() == 1) {
            int id1 = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String leixing = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("leixing")), password);
            String zhanghao = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("zhanghao")), password);
            String pass = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("password")), password);
            Main_data main_data = new Main_data(leixing, zhanghao, pass);
            main_data.setId(id1);
            return main_data;
        } else
            return null;
    }

}
