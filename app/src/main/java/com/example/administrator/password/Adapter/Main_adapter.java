package com.example.administrator.password.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.administrator.password.Activity.MainActivity;
import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.R;
import com.example.administrator.password.View.Itemview;
import com.example.administrator.password.WatchText.Main_TextWatcher;

import java.util.List;

/**
 * Created by Administrator on 2018\5\18 0018.
 */

public class Main_adapter extends RecyclerView.Adapter {
    private Context context;
    private List<Main_data> datas;
    private int[] colors = new int[4];
    //    private Itemview view;
    private Scroller scroller;
    private Boolean xuanze = false;
   private RecyclerView recyclerView;

    //    private Main_TextWatcher main_textWatcher;
    public Main_adapter(Context context, List<Main_data> datas) {
        super();
        this.context = context;
        this.datas = datas;

        colors[0] = 0xff000000;
        colors[1] = 0xffffff00;
        colors[2] = 0xff00bfff;
        colors[3] = 0xff191970;
//        //创建edittext 内容观察者
//        main_textWatcher=new Main_TextWatcher();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Itemview view = (Itemview) LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        //为下方的view左滑 都生成一个新的scroller，并存到Itemview中，这样就可以一个view对应一个scroller，不会产生左滑错乱
        scroller = new Scroller(context);
        view.setScroller(scroller);
        return new Main_viewholder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Main_viewholder main_viewholder = (Main_viewholder) holder;
        final Main_data main_data = datas.get(position);
        main_viewholder.leixing.setTag(position);
        main_viewholder.leixing.setTextColor(colors[position % 4]);
        //给要输入内容的Edittext添加内容观察者，调用下方的方法
        main_viewholder.leixing.addTextChangedListener(creat_main_textWatcher(main_data.getId(), "leixing"));
        main_viewholder.leixing.setText(main_data.getLeixing()+"  id :"+main_data.getId());
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

        //给要输入内容的Edittext添加内容观察者，调用下方的方法
        main_viewholder.zhanghao.setText(main_data.getZhanghao());
//        main_viewholder.zhanghao.addTextChangedListener(creat_main_textWatcher(main_data.getId(), "zhanghao"));
        //给要输入内容的Edittext添加内容观察者，调用下方的方法
        main_viewholder.password.setText(main_data.getPassword());
//        main_viewholder.password.addTextChangedListener(creat_main_textWatcher(main_data.getId(), "password"));
        if (xuanze) {
            main_viewholder.xuanze.setVisibility(View.VISIBLE);
        } else {
            main_viewholder.xuanze.setVisibility(View.GONE);
        }
        //弹出右边抽屉内容的动画，view 被我自定义了 在下方可看
        main_viewholder.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allfuyuan(recyclerView,position);
                if (!main_data.getLeft()) {
                    main_data.setLeft(true);
                    main_viewholder.left.setBackgroundResource(R.drawable.right);
                    int left = main_viewholder.getView().getRight() - main_viewholder.zhanghao.getRight();
                    ((Itemview) main_viewholder.getView()).getScroller().startScroll(main_viewholder.getView().getScrollX(), 0, left, 0, 500);
                    main_viewholder.getView().invalidate();
                } else {
                    main_data.setLeft(false);
                    main_viewholder.left.setBackgroundResource(R.drawable.left);
                    ((Itemview) main_viewholder.getView()).getScroller().startScroll(main_viewholder.getView().getScrollX(), 0, -main_viewholder.getView().getScrollX(), 0, 500);
                    main_viewholder.getView().invalidate();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView=recyclerView;
    }

    class Main_viewholder extends RecyclerView.ViewHolder {
        private CheckBox xuanze;
        private EditText leixing;
        private Button left;
        private EditText zhanghao;
        private EditText password;
        private View view;

        public Main_viewholder(View itemView) {
            super(itemView);
            xuanze = (CheckBox) itemView.findViewById(R.id.Main_xuanze);
            leixing = (EditText) itemView.findViewById(R.id.Main_leixing);
            left = (Button) itemView.findViewById(R.id.Main_left);
            zhanghao = (EditText) itemView.findViewById(R.id.Main_zhanghao);
            password = (EditText) itemView.findViewById(R.id.Main_password);
            //将上面onCreateViewHolder中生成的item rootview都保存到这里，方便在onBindViewHolder中取出。
            this.view = itemView;
        }

        public View getView() {
            return view;
        }
    }

    public void setXuanze(Boolean xuanze) {
        this.xuanze = xuanze;
    }

    public Boolean getXuanze() {
        return xuanze;
    }

    //给要输入内容的EditText生成内容观察者 并且设定位置信心
    public Main_TextWatcher creat_main_textWatcher(int id, String key) {
        Main_TextWatcher main_textWatcher = new Main_TextWatcher();
        main_textWatcher.setXinxi(id, key);
        return main_textWatcher;
    }
    //用于将所有划出的左滑子view 复原
    public void allfuyuan(RecyclerView recyclerView,int position){
        for (int i=0;i<datas.size();i++){
            if (datas.get(i).getLeft()&&i!=position){
                datas.get(i).setLeft(false);
                Itemview itemview=(Itemview) recyclerView.getChildAt(i);
                Button button=itemview.findViewById(R.id.Main_left);
                button.setBackgroundResource(R.drawable.left);
                itemview.fuyuan();
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
//    class Itemview extends LinearLayout{
//        private Scroller scroller;
//
//        public Itemview(Context context) {
//            super(context);
//        }
//
//        public Itemview(Context context, @Nullable AttributeSet attrs) {
//            super(context, attrs);
//        }
//
//        public void setScroller(Scroller scroller) {
//            this.scroller = scroller;
//        }
//
//        @Override
//        public void computeScroll() {
//            super.computeScroll();
//            if (scroller.computeScrollOffset()) {
////            out.println("scroller.getCurrX()",scroller.getCurrX());
//                scrollTo(scroller.getCurrX(), scroller.getCurrY());
//                invalidate();
//            }
//        }
//    }

}
