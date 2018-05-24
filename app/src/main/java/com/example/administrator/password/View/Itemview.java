package com.example.administrator.password.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2018\5\23 0023.
 */

public class Itemview extends LinearLayout {
    private Scroller scroller;

    public Itemview(Context context) {
        super(context);
    }

    public Itemview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScroller(Scroller scroller) {
        this.scroller = scroller;
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
}


