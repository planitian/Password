package com.example.administrator.password.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.administrator.password.Adapter.Main_adapter;
import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.Dao.Maindao;
import com.example.administrator.password.Fragment.AddFragment;
import com.example.administrator.password.R;
import com.example.administrator.password.View.Itemview;
import com.example.administrator.password.WatchText.Main_TextWatcher;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddFragment.AddCallback ,View.OnClickListener,Main_TextWatcher.TextCallback{
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
        datas.clear();
        datas.addAll(temp);
        main_adapter.notifyDataSetChanged();
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
                   if (datas.get(i).getXuanze())
                       Maindao.Main_del(String.valueOf(datas.get(i).getId()),"id");
                   datas.remove(i);
               }
               main_adapter.notifyDataSetChanged();
                rede.performClick();
                break;

        }
    }

    @Override
    public void setdata(Editable s) {

    }
}
