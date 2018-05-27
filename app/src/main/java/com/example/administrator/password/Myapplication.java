package com.example.administrator.password;

import android.app.Application;

import com.example.administrator.password.Dao.Maindao;

/**
 * Created by Administrator on 2018\5\21 0021.
 */

public class Myapplication extends Application {
    private Maindao maindao;
    public Myapplication() {
        maindao=new Maindao(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


}
