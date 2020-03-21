package com.example.administrator.password.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018\5\23 0023.
 */

public class Itemview extends LinearLayout {
    private Scroller scroller;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }
    public Itemview(Context context) {
        super(context);
    }

    public Itemview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScroller(Scroller scroller) {
        this.scroller = scroller;
    }

    public Scroller getScroller() {
        return scroller;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
//            out.println("scroller.getCurrX()",scroller.getCurrX());
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }
    //用于将左滑还原，在recycleview哪里调用，实现 自动复原
    public void fuyuan(){
        scroller.startScroll(getScrollX(),0,-getScrollX(),0);
        invalidate();
    }

}


