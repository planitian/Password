package com.example.administrator.password.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Scroller;

import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.R;

import java.util.List;

/**
 * Created by Administrator on 2018\5\18 0018.
 */

public class Main_adapter extends RecyclerView.Adapter {
    private Context context;
    private List<Main_data> datas;
    private int[] colors=new  int[4];
   private Itemview  view;
    private Scroller scroller;
    public Main_adapter(Context context, List<Main_data> datas) {
        super();
        this.context=context;
        this.datas=datas;
        scroller=new Scroller(context);
        colors[0]=0xff000000;
        colors[1]=0xffffff00;
        colors[2]=0xff00bfff;
        colors[3]=0xffc6e2ff;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= (Itemview)LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        view.setScroller(scroller);
        return new Main_viewholder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         final Main_viewholder main_viewholder=(Main_viewholder)holder;
        final Main_data main_data=datas.get(position);
        main_viewholder.leixing.setText(main_data.getLeixing());
        main_viewholder.leixing.setTextColor(colors[position%4]);
        main_viewholder.xuanze.setChecked(main_data.getXuanze());
        main_viewholder.xuanze.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  main_data.setXuanze(isChecked);
            }
        });
        if (main_data.getLeft())
            main_viewholder.left.setBackgroundResource(R.drawable.right);
        else
            main_viewholder.left.setBackgroundResource(R.drawable.left);
        main_viewholder.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_data.setLeft(true);
                main_viewholder.left.setBackgroundResource(R.drawable.right);
               int left=view.getRight()-main_viewholder.left.getRight();
                scroller.startScroll(view.getScrollX(),0,left,0,500);
                view.invalidate();
            }
        });

        main_viewholder.zhanghao.setText(main_data.getZhanghao());
        main_viewholder.password.setText(main_data.getPassword());
    }

    @Override
    public int getItemCount() {
      return datas.size();
    }

    class Main_viewholder extends RecyclerView.ViewHolder {
        private CheckBox xuanze;
        private EditText leixing;
        private Button left;
        private EditText zhanghao;
        private EditText password;
        public Main_viewholder(View itemView) {
            super(itemView);
            xuanze=(CheckBox)itemView.findViewById(R.id.Main_xuanze);
            leixing=(EditText)itemView.findViewById(R.id.Main_leixing);
            left=(Button)itemView.findViewById(R.id.Main_left);
            zhanghao=(EditText)itemView.findViewById(R.id.Main_zhanghao);
            password=(EditText)itemView.findViewById(R.id.Main_password);
        }
    }

    class Itemview extends View {
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

}
