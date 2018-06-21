package com.example.administrator.password.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class Main_recycle extends RecyclerView {
    private  PopCallback popCallback;

    public void setPopCallback(PopCallback popCallback) {
        this.popCallback = popCallback;
    }

    public Main_recycle(Context context) {
        super(context);
    }

    public Main_recycle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Main_recycle(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean result=super.onInterceptTouchEvent(e);
        if (result){
            popCallback.hide();
        }
        return result;
    }

    public interface PopCallback{
        void hide();
    }
}
