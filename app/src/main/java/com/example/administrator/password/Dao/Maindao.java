package com.example.administrator.password.Dao;

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
    private  static   String password ="plani";
    private static Myslqdata myslqdata;
    private static SQLiteDatabase sqLiteDatabase;
    private static Maindao maindao;
  //单例模式 虽然没用在这里
    public static Maindao getInstance(Context context) {
        if (maindao == null) {
            maindao = new Maindao();
        }
        return maindao;

    }

    public static void Main_insert(Main_data main_data) {
        String leixing = AEShelper.encrypt(main_data.getLeixing(), password);
        String zhanghao=AEShelper.encrypt(main_data.getZhanghao(),password);
        String pass=AEShelper.encrypt(main_data.getPassword(),password);
        sqLiteDatabase = myslqdata.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("leixing",leixing );
        contentValues.put("zhanghao",zhanghao);
        contentValues.put("password",pass);
        sqLiteDatabase.insert("Main", null, contentValues);
        sqLiteDatabase.close();
    }
    public  static void Main_del(String src,String key){
        sqLiteDatabase=myslqdata.getWritableDatabase();
        sqLiteDatabase.delete("Main",key+"=?",new String[]{src});
    }

    public  static  void  Main_update(String src,String key,String where,String wherearg){
        sqLiteDatabase=myslqdata.getWritableDatabase();
        String content =AEShelper.encrypt(src,password);
        ContentValues contentValues = new ContentValues();
        contentValues.put(key,content);
        sqLiteDatabase.update("Main",contentValues,where+"=?",new String[]{wherearg});
        sqLiteDatabase.close();
    }

    public static List<Main_data> Main_queryall(){
        sqLiteDatabase=myslqdata.getWritableDatabase();
         Cursor cursor= sqLiteDatabase.query("Main",null,null,null,null,null,null);
        List<Main_data> datas=new ArrayList<>();
        if (cursor.moveToFirst()){
            for (int i=0;i<cursor.getCount();i++){
                cursor.move(i);

                int id=cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String leixing=AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("leixing")),password);
                String zhanghao=AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("zhanghao")),password);
                String pass=AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("password")),password);
                Main_data main_data=new Main_data(leixing,zhanghao,pass);
                datas.add(main_data);
            }
        }
    }

}
