package com.example.administrator.password.ItemDecoration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.password.R;
import com.example.administrator.password.View.Itemview;

public class Main_Decoration extends RecyclerView.ItemDecoration {
    private int jishu=0;
    private Context context;
    private Paint paint;
    private int xiahua;
    public Main_Decoration(Context context) {
        super();
        this.context=context;
        paint=new Paint();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
          xiahua=context.getResources().getDimensionPixelSize(R.dimen.Decoration_xiahuaxian);
//         System.out.println("Decoration "+xiahua+"jishu :"+jishu);
         jishu++;
         //这里面的parent.getChildCount()得到的数字并不是固定的 ，会从1一直到最大
//         System.out.println("parent.getChildCount():"+parent.getChildCount()+"state.getItemCount()    :"+state.getItemCount());
         outRect.bottom=xiahua;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int counts=parent.getChildCount();
//        int counts=state.getItemCount();
        paint.setColor(context.getResources().getColor(R.color.Tomato,null));
        paint.setAntiAlias(true);
        for (int i=0;i<counts;i++){
            Itemview view=(Itemview)parent.getChildAt(i);
            View view1=view.findViewById(R.id.Main_leixing);
                int left=view1.getLeft();
                int right=view.getRight();
                int bottoom=view.getBottom();
                c.drawRect(left,bottoom,right,bottoom+xiahua,paint);


        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
