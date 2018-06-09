package com.example.administrator.password.ItemDecoration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.password.R;

public class Main_Decoration extends RecyclerView.ItemDecoration {
    private Context context;
    private Paint paint;
    public Main_Decoration(Context context) {
        super();
        this.context=context;
        paint=new Paint();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
         int shuchu=context.getResources().getDimensionPixelSize(R.dimen.addxinxi_margintop);
         System.out.println("Decoration "+shuchu);
        outRect.bottom=shuchu;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int counts=parent.getChildCount();
        paint.setColor(context.getResources().getColor(R.color.Tomato,null));
        paint.setAntiAlias(true);
        for (int i=0;i<counts;i++){
            View view=parent.getChildAt(i);
            int left=view.getLeft();
            int top=view.getTop();
            int right=view.getRight();
            int bottoom=view.getBottom();
            c.drawRect(left,bottoom,right,bottoom+22,paint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
