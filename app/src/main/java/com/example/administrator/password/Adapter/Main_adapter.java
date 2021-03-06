package com.example.administrator.password.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Scroller;

import androidx.recyclerview.widget.RecyclerView;

import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.R;
import com.example.administrator.password.View.Itemview;
import com.example.administrator.password.View.Main_recycle;
import com.example.administrator.password.WatchText.Main_TextWatcher;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * Created by Administrator on 2018\5\18 0018.
 */

public class Main_adapter extends RecyclerView.Adapter implements Main_recycle.PopCallback {
    private Context context;
    private List<Main_data> datas;
    private int[] colors = new int[4];
    // private Itemview view;
    private Scroller scroller;
    private Boolean xuanze = false;
    private RecyclerView recyclerView;
    private int lastposition=-1;
    private PopupWindow popupWindow;
    private int position_fuzhi=-1;
    private int[] popup_width;
    private ClipboardManager clipboardManager;

    public void setClipboardManager(ClipboardManager clipboardManager) {
        this.clipboardManager = clipboardManager;
    }

    // private Main_TextWatcher main_textWatcher;
    public Main_adapter(Context context, List<Main_data> datas) {
        super();
        this.context = context;
        this.datas = datas;

        colors[0] = 0xff000000;
        colors[1] = 0xffEE30A7;
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
        if (main_viewholder.leixing.getTag() == null) {
            Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "leixing", main_data);
            main_viewholder.leixing.addTextChangedListener(main_textWatcher);
            Cuncu cuncu = new Cuncu(main_textWatcher, position);
            main_viewholder.leixing.setTag(cuncu);
        } else {
            Cuncu cuncu = (Cuncu) main_viewholder.leixing.getTag();
            if (cuncu.getPosition() != position) {
                Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "leixing", main_data);
                main_viewholder.leixing.removeTextChangedListener(cuncu.getMain_textWatcher());
                main_viewholder.leixing.addTextChangedListener(main_textWatcher);
                Cuncu cuncu1 = new Cuncu(main_textWatcher, position);
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
        //和下面的所有item复位结合的功能
        if (main_data.getLeft()) {
            main_viewholder.left.setBackgroundResource(R.drawable.right);
        } else {
            main_viewholder.left.setBackgroundResource(R.drawable.left);
        }
        if (main_viewholder.getView().getScrollX() != 0 && !main_data.getLeft()) {
          main_viewholder.getView().scrollTo(0,0);
//            System.out.println("Main_adapter"+this.getClass().getName()+main_viewholder.getLayoutPosition()+"    "+main_viewholder.getAdapterPosition());
        }

        //给要输入内容的Edittext添加内容观察者，调用下方的方法
        if (main_viewholder.zhanghao.getTag() == null) {
            Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "zhanghao", main_data);
            main_viewholder.zhanghao.addTextChangedListener(main_textWatcher);
            Cuncu cuncu = new Cuncu(main_textWatcher, position);
            main_viewholder.zhanghao.setTag(cuncu);
        } else {
            Cuncu cuncu = (Cuncu) main_viewholder.zhanghao.getTag();
            if (cuncu.getPosition() != position) {
                Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "zhanghao", main_data);
                main_viewholder.zhanghao.removeTextChangedListener(cuncu.getMain_textWatcher());
                main_viewholder.zhanghao.addTextChangedListener(main_textWatcher);
                Cuncu cuncu1 = new Cuncu(main_textWatcher, position);
                main_viewholder.zhanghao.setTag(cuncu1);
            }
        }
        main_viewholder.zhanghao.setText(main_data.getZhanghao());
        main_viewholder.zhanghao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showPopupwindow(main_viewholder.zhanghao,position);
            }
        });
        //给要输入内容的Edittext添加内容观察者，调用下方的方法
        if (main_viewholder.password.getTag() == null) {
            Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "password", main_data);
            main_viewholder.password.addTextChangedListener(main_textWatcher);
            Cuncu cuncu = new Cuncu(main_textWatcher, position);
            main_viewholder.password.setTag(cuncu);
        } else {
            Cuncu cuncu = (Cuncu) main_viewholder.password.getTag();
            if (cuncu.getPosition() != position) {
                Main_TextWatcher main_textWatcher = creat_main_textWatcher(main_data.getId(), "password", main_data);
                main_viewholder.password.removeTextChangedListener(cuncu.getMain_textWatcher());
                main_viewholder.password.addTextChangedListener(main_textWatcher);
                Cuncu cuncu1 = new Cuncu(main_textWatcher, position);
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
                allfuyuan(recyclerView, position);
                if (popupWindow==null){
                    createpopwindow(context,main_viewholder.zhanghao,position);
                }else {
                    if (main_data.getLeft())
                    {
                        hide();
                    }else {
                        popupWindow.dismiss();
                        showPopupwindow(main_viewholder.zhanghao,position);
                    }
                }

                //因为recyclerView.getChildCount();得不到所有的item 此方法作废
//                if (lastposition!=-1){
//                     int count=recyclerView.getChildCount();
//                     if (lastposition+1<count){
//                         Itemview itemview=(Itemview) recyclerView.getChildAt(lastposition);
//                         itemview.findViewById(R.id.Main_left).setBackgroundResource(R.drawable.left);
//                         itemview.getScroller().startScroll(itemview.getScrollX(),0,-itemview.getScrollX(),0);
//                         itemview.postInvalidate();
//                     }else {
//                         Itemview itemview=(Itemview) recyclerView.getChildAt(lastposition%count);
//                         itemview.findViewById(R.id.Main_left).setBackgroundResource(R.drawable.left);
//                         itemview.getScroller().startScroll(itemview.getScrollX(),0,-itemview.getScrollX(),0);
//                         itemview.postInvalidate();
//                     }
//                }
//                lastposition=position;
                if (!main_data.getLeft()) {
                    main_data.setLeft(true);
                    main_viewholder.left.setBackgroundResource(R.drawable.right);
                    int[] zuobiao = new int[2];
                    main_viewholder.zhanghao.getLocationInWindow(zuobiao);
                    int[] diyige = new int[2];
                    main_viewholder.zhanghao.getLocationOnScreen(diyige);
//                    main_viewholder.getView().getLocationInWindow(diyige);
                    System.out.println("diyige:" + diyige[0] + "sss" + diyige[1]);
                    System.out.println("绝对坐标：" + zuobiao[0] + "坐标二：" + zuobiao[1]);
                    int left = main_viewholder.getView().getRight() - main_viewholder.zhanghao.getRight();
                    ((Itemview) main_viewholder.getView()).getScroller().startScroll(main_viewholder.getView().getScrollX(), 0, main_viewholder.zhanghao.getRight(), 0, 500);
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
        this.recyclerView = recyclerView;
    }

    @Override
    public void hide() {
         if (popupWindow!=null){
             popupWindow.dismiss();
         }
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
    public Main_TextWatcher creat_main_textWatcher(int id, String key, Main_data main_data) {
        Main_TextWatcher main_textWatcher = new Main_TextWatcher();
        main_textWatcher.setXinxi(id, key, main_data);
        return main_textWatcher;
    }

    //用于将所有划出的左滑子view 复原
    public void allfuyuan(RecyclerView recyclerView, int position) {
        System.out.println("recyclerView.getChildCount();" + recyclerView.getChildCount());
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getLeft() && i != position) {
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

    class Cuncu {
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

    //生成popwindow用于复制账号 密码
    public void createpopwindow(final Context context, View par, final int position){
        position_fuzhi=position;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.caozuo,null);
        popup_width=unDisplayViewSize(view);
        Button fuzhi_zhanghao=(Button)view.findViewById(R.id.caozuo_fuzhi_zhanghao);
        fuzhi_zhanghao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData=ClipData.newPlainText("zhanghao",datas.get(position_fuzhi).getZhanghao().trim());
                System.out.println("Main_adapter createpopwindow "+position_fuzhi+datas.get(position_fuzhi).getZhanghao());
                clipboardManager.setPrimaryClip(clipData);
                Snackbar.make((View) (recyclerView.getParent()), "账号内容："+datas.get(position_fuzhi).getZhanghao(), Snackbar.LENGTH_SHORT)
                       .show();
            }
        });
        Button fuzhi_password=(Button)view.findViewById(R.id.caozuo_fuzhi_password);
        fuzhi_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData=ClipData.newPlainText("password",datas.get(position_fuzhi).getPassword().trim());
                System.out.println("Main_adapter createpopwindow "+position_fuzhi+datas.get(position_fuzhi).getPassword());
                clipboardManager.setPrimaryClip(clipData);
                Snackbar.make((View) (recyclerView.getParent()), "密码内容："+datas.get(position_fuzhi).getPassword(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
        DisplayMetrics display= context.getResources().getDisplayMetrics();
        popupWindow=new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT,-2);
        showPopupwindow(par,position);
    }

    //显示popupwindow，并重新定位
    public  void showPopupwindow(View view,int position){
        position_fuzhi=position;
        if (!popupWindow.isShowing()){
            int[] weizhi=new int[2];
            view.getLocationOnScreen(weizhi);
            if (popup_width[1]<weizhi[1]){
                popupWindow.showAsDropDown(view,view.getWidth()/4,-3*view.getHeight()-20);
            }else {
                popupWindow.showAsDropDown(view,view.getWidth(),view.getHeight());
            }

        }
    }


    public int[] unDisplayViewSize(View view) {
        int size[] = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
        return size;
    }
}
