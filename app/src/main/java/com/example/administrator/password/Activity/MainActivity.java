package com.example.administrator.password.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.administrator.password.Adapter.Main_adapter;
import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.Dao.Maindao;
import com.example.administrator.password.Fragment.AddFragment;
import com.example.administrator.password.ItemDecoration.Main_Decoration;
import com.example.administrator.password.R;
import com.example.administrator.password.View.Itemview;
import com.example.administrator.password.WatchText.Main_TextWatcher;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddFragment.AddCallback ,View.OnClickListener{
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Button rede;
    private Button add;
    private CheckBox main_all;
    private Button main_fanxuan;
    private Button main_del;
    private Main_adapter main_adapter;
    private List<Main_data> datas;
    private LinearLayout caozuo;
    private SearchView searchView;
   android.os.Handler handler=new android.os.Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case 1:searchView.clearFocus();
                     break;
           }
       }
   };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.Main_Toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.Main_recy);
        rede = (Button) findViewById(R.id.Main_rede);
        add = (Button) findViewById(R.id.Main_add);
        searchView=(SearchView)findViewById(R.id.searc);
        //搜索按钮的一些代码 主要是点击事件
        searchView.setIconified(true);
//        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                 System.out.println("setOnQueryTextFocusChangeListener"+hasFocus);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("onQueryTextSubmit   "+query);
                for (int i=0;i<datas.size();i++){
                    if (datas.get(i).getLeixing().contains(query)){
                        datas.remove(i);
                        i--;
                    }
                }
                main_adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("onQueryTextChange   "+newText);
                datas.clear();
                datas.addAll(Maindao.Main_queryall());
                for (int i=0;i<datas.size();i++){
                    if (!datas.get(i).getLeixing().contains(newText)){
                        datas.remove(i);
                        i--;
                    }
                }
                main_adapter.notifyDataSetChanged();
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                datas.clear();
                datas.addAll(Maindao.Main_queryall());
                main_adapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
                 System.out.println(">>>>>>>>>>>>"+this.getClass().getName()+searchView.isIconified());
                return true;
            }
        });

        caozuo = (LinearLayout) findViewById(R.id.Main_caozuo);
        main_all=(CheckBox)findViewById(R.id.Main_all);
        main_fanxuan=(Button)findViewById(R.id.Main_fanxuan);
        main_del=(Button)findViewById(R.id.Main_del);
        main_all.setOnClickListener(this);
        main_fanxuan.setOnClickListener(this);
        main_del.setOnClickListener(this);
        //得到数据库中的所有数据 ，并以Bean的方式返回List<Main_data>
        datas = Maindao.Main_queryall();
        main_adapter = new Main_adapter(this, datas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(main_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayout.VERTICAL));
        recyclerView.addItemDecoration(new Main_Decoration(MainActivity.this));
        Add_shijian(add);
        Rede_shijian(rede);
    }

    //添加add点击事件
    public void Add_shijian(Button add) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFragment addFragment = new AddFragment();
                addFragment.show(getFragmentManager(), "addFragment");
            }
        });
    }

    //添加rede点击事件
    public void Rede_shijian(Button rede) {
        rede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!main_adapter.getXuanze()) {
                    caozuo.setVisibility(View.VISIBLE);
                    main_adapter.setXuanze(true);
                    main_adapter.notifyDataSetChanged();
                }else {
                    caozuo.setVisibility(View.GONE);
                    for (Main_data main_data:datas){
                        main_data.setXuanze(false);
                    }
                    main_adapter.setXuanze(false);
                    main_all.setChecked(false);
                    main_adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void noti() {
        List<Main_data> temp = Maindao.Main_queryall();
        Main_data main_data=temp.get(temp.size()-1);
        datas.add(main_data);
        main_adapter.notifyItemInserted(temp.size()-1);
        main_adapter.notifyItemChanged(temp.size()-1);
        recyclerView.scrollToPosition(temp.size()-1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Main_all:
                if (main_all.isChecked()){
                    for (Main_data main_data:datas){
                        main_data.setXuanze(true);
                    }
                }else {
                    for (Main_data main_data:datas){
                        main_data.setXuanze(false);
                    }
                }

                main_adapter.notifyDataSetChanged();
                break;
            case R.id.Main_fanxuan:
                main_all.setChecked(false);
                for (Main_data main_data:datas){
                    if (main_data.getXuanze())
                        main_data.setXuanze(false);
                    else
                        main_data.setXuanze(true);
                }
                //用于选择反选时候，如果选择框全部选择，全选框应当也是选择的

              int a=0;//计数，用来累计数据源中选择的次数，如果和size相同，则全部选择
              for (int i=0;i<datas.size();i++){
                 if (datas.get(i).getXuanze()){
                     a++;
                 }
            }
            if (a==datas.size()){
                main_all.setChecked(true);
            }
                main_adapter.notifyDataSetChanged();
                break;
            case R.id.Main_del:
               for (int i=0;i<datas.size();i++){
                   if (datas.get(i).getXuanze()) {
                       Maindao.Main_del(String.valueOf(datas.get(i).getId()),"id");
                       datas.remove(i);//当数组移除一个数据时候，当前数据位置会被下一位数据顶替，所以需要将i减一，这样下一位数据才能获取到
                       main_adapter.notifyItemRemoved(i);
                       main_adapter.notifyItemRangeChanged(i,datas.size()-i-1);
                       //这一循环结束后，i会自动加一，造成下一次循环，数组中的下一位数据被替换到当前位置，造成数据没有判断 所以需要减一
                       i--;
                   }
               }
                rede.performClick();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        System.out.println("onBackPressed");
        super.onBackPressed();
    }
}
