package com.example.administrator.password.WatchText;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.Dao.Maindao;

/**
 * Created by Administrator on 2018\5\20 0020.
 */

public class Main_TextWatcher implements android.text.TextWatcher {
    private int id;
    private String key;
    private Main_data main_data;


    public void setXinxi(int id, String key,Main_data main_data) {
        this.key = key;
        this.id = id;
        this.main_data=main_data;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        Log.d("Textwatch", s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

//            Log.d("afterTextChanged", s.toString().trim());
            if (s.toString().trim() == "" || s.toString().trim().length() == 0 || s.toString().trim().equals(null)) {
                //空语句 不做任何事
            } else {
                Maindao.Main_update(s.toString().trim(), key, "id", String.valueOf(id));
                switch (key){
                    case "leixing":main_data.setLeixing(s.toString().trim());
                        break;
                    case "zhanghao":main_data.setZhanghao(s.toString().trim());
                        break;
                    case "password":main_data.setPassword(s.toString().trim());
                        break;
                }

            }

    }


}
