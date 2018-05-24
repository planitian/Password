package com.example.administrator.password.Dao;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.Encryption.AEShelper;
import com.example.administrator.password.Sql.Myslqdata;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.password.R.string.password;
import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018\5\23 0023.
 */
public class MaindaoTest {
    private static String password = "plani";
    @Test
    public void main_queryall() throws Exception {
         Myslqdata myslqdata = new Myslqdata(new Application() , "shuju", null, 1);
        SQLiteDatabase sqLiteDatabase = myslqdata.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Main", null, null, null, null, null, null);
        List<Main_data> datas = new ArrayList<>();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String leixing = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("leixing")), password);
                String zhanghao = AEShelper.decrypt(cursor.getString(2), password);
                System.out.println(cursor.getString(1)+"    "+cursor.getColumnCount()+cursor.getCount());
                String pass = AEShelper.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("password")), password);
                Main_data main_data = new Main_data(leixing, zhanghao, pass);
                main_data.setId(id);
                System.out.println("Main_data的数据"+main_data.toString());
                datas.add(main_data);
            }
        }
    }

}