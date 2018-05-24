package com.example.administrator.password.Bean;

/**
 * Created by Administrator on 2018\5\19 0019.
 */

public class Main_data {
    private int id;
    private String leixing;
    private String zhanghao;
    private String password;
    private Boolean xuanze = false;
    private Boolean left = false;

    public Main_data(String leixing, String zhanghao, String password) {
        this.leixing = leixing;
        this.zhanghao = zhanghao;
        this.password = password;
    }

    @Override
    public String toString() {
        String result = "leixing:" + leixing + "   zhanghao:" + zhanghao + "   password:" + password;
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getLeft() {
        return left;
    }

    public void setLeft(Boolean left) {
        this.left = left;
    }

    public Boolean getXuanze() {
        return xuanze;
    }

    public void setXuanze(Boolean xuanze) {
        this.xuanze = xuanze;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getZhanghao() {
        return zhanghao;
    }

    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
