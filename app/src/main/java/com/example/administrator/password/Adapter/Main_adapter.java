package com.example.administrator.password.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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

import java.util.ArrayList;
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
    private List<Integer> textwatch_fuyong=new ArrayList<>();
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
        main_viewholder.leixing.setTextColor(colors[position % 4]);

//        System.out.println(main_textWatcher.hashCode());

        //以下一段代码是为了防止recycleview复用机制导致的item之间的textwatcher混用，导致的数据错乱，给每一个item都生成一个新的textwatch
        //最好在setext()前面就加入textwatch，这样setText时候，就会将设置的数据写入到数据库中，保证了数据的一致性。
        //但是这段代码有一个致命的缺陷
//        if (!(main_viewholder.leixing.getTag()instanceof  Main_TextWatcher)){
//            Main_TextWatcher main_textWatcher=creat_main_textWatcher(main_data.getId(),"leixing",main_data);
//            main_viewholder.leixing.setTag(main_textWatcher);
//            main_viewholder.leixing.addTextChangedListener(main_textWatcher);
//        }else {
//            Main_TextWatcher main_textWatcher=creat_main_textWatcher(main_data.getId(),"leixing",main_data);
//            main_viewholder.leixing.removeTextChangedListener((Main_TextWatcher)main_viewholder.leixing.getTag());
//            main_viewholder.leixing.addTextChangedListener(main_textWatcher);
//        }
        if (main_viewholder.leixing.getTag()==null) {
            Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "leixing",main_data);
            main_viewholder.leixing.addTextChangedListener(main_textWatcher);
            Cuncu cuncu=new Cuncu(main_textWatcher,position);
            main_viewholder.leixing.setTag(cuncu);
        }else {
            Cuncu cuncu=(Cuncu)main_viewholder.leixing.getTag();
            if (cuncu.getPosition()!=position){
                Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "leixing",main_data);
                main_viewholder.leixing.removeTextChangedListener(cuncu.getMain_textWatcher());
                main_viewholder.leixing.addTextChangedListener(main_textWatcher);
                Cuncu cuncu1=new Cuncu(main_textWatcher,position);
                main_viewholder.leixing.setTag(cuncu1);
            }
        }
        main_viewholder.leixing.setText(main_data.getLeixing());

        //给要输入内容的Edittext添加内容观察者，调用下方的方法

//       if (!textwatch_fuyong.contains(position)){
//           System.out.println("复用"+position);
//           Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "leixing");
//           main_viewholder.leixing.addTextChangedListener(main_textWatcher);
//           textwatch_fuyong.add(position);
//       }
        main_viewholder.xuanze.setChecked(main_data.getXuanze());
        main_viewholder.xuanze.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                main_data.setXuanze(isChecked);
            }
        });
        if (main_data.getLeft()){
            main_viewholder.left.setBackgroundResource(R.drawable.right);
        }
        else {
            main_viewholder.left.setBackgroundResource(R.drawable.left);
        }
     if (main_viewholder.getView().getScrollX()!=0){
         ((Itemview) main_viewholder.getView()).getScroller().startScroll(main_viewholder.getView().getScrollX(), 0, -main_viewholder.getView().getScrollX(), 0, 500);
         main_viewholder.getView().invalidate();
     }

        //给要输入内容的Edittext添加内容观察者，调用下方的方法
        if (main_viewholder.zhanghao.getTag()==null) {
            Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "zhanghao",main_data);
            main_viewholder.zhanghao.addTextChangedListener(main_textWatcher);
            Cuncu cuncu=new Cuncu(main_textWatcher,position);
            main_viewholder.zhanghao.setTag(cuncu);
        }else {
            Cuncu cuncu=(Cuncu)main_viewholder.zhanghao.getTag();
            if (cuncu.getPosition()!=position){
                Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "zhanghao",main_data);
                main_viewholder.zhanghao.removeTextChangedListener(cuncu.getMain_textWatcher());
                main_viewholder.zhanghao.addTextChangedListener(main_textWatcher);
                Cuncu cuncu1=new Cuncu(main_textWatcher,position);
                main_viewholder.zhanghao.setTag(cuncu1);
            }
        }
        main_viewholder.zhanghao.setText(main_data.getZhanghao());

        //给要输入内容的Edittext添加内容观察者，调用下方的方法
        if (main_viewholder.password.getTag()==null) {
            Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "password",main_data);
            main_viewholder.password.addTextChangedListener(main_textWatcher);
            Cuncu cuncu=new Cuncu(main_textWatcher,position);
            main_viewholder.password.setTag(cuncu);
        }else {
            Cuncu cuncu=(Cuncu)main_viewholder.password.getTag();
            if (cuncu.getPosition()!=position){
                Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "password",main_data);
                main_viewholder.password.removeTextChangedListener(cuncu.getMain_textWatcher());
                main_viewholder.password.addTextChangedListener(main_textWatcher);
                Cuncu cuncu1=new Cuncu(main_textWatcher,position);
                main_viewholder.password.setTag(cuncu1);
            }
        }
        main_viewholder.password.setText(main_data.getPassword());

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
        //设置是否复用
//        main_viewholder.setIsRecyclable(false);
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
    public Main_TextWatcher creat_main_textWatcher(int id, String key,Main_data main_data) {
        Main_TextWatcher main_textWatcher = new Main_TextWatcher();
        main_textWatcher.setXinxi(id, key,main_data);
        return main_textWatcher;
    }
    //用于将所有划出的左滑子view 复原
    public void allfuyuan(RecyclerView recyclerView,int position){
        System.out.println("recyclerView.getChildCount();"+recyclerView.getChildCount());
        for (int i=0;i<datas.size();i++){
            if (datas.get(i).getLeft()&&i!=position){
                datas.get(i).setLeft(false);
                //使用下面语句对第position位置item进行数据的重新绑定和刷新
                notifyItemChanged(i);

//                Itemview itemview=(Itemview) recyclerView.getChildAt(i%recyclerView.getChildCount());
//                Button button=itemview.findViewById(R.id.Main_left);
//                button.setBackgroundResource(R.drawable.left);
//                itemview.fuyuan();
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
    class Cuncu{
        private Main_TextWatcher main_textWatcher;
        private int position;

        public Cuncu(Main_TextWatcher main_textWatcher, int position) {
            this.main_textWatcher = main_textWatcher;
            this.position = position;
        }

        public Main_TextWatcher getMain_textWatcher() {
            return main_textWatcher;
        }

        public int getPosition() {
            return position;
        }
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
