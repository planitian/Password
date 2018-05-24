package com.example.administrator.password.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.password.Adapter.Main_adapter;
import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.Dao.Maindao;
import com.example.administrator.password.Fragment.AddFragment;
import com.example.administrator.password.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddFragment.AddCallback {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Button rede;
    private  Button add;
    private Main_adapter main_adapter;
    private List<Main_data> datas;
    private LinearLayout caozuo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.Main_Toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.Main_recy);
        rede=(Button)findViewById(R.id.Main_rede);
        add=(Button)findViewById(R.id.Main_add);
        caozuo=(LinearLayout)findViewById(R.id.Main_caozuo);
        //得到数据库中的所有数据 ，并以Bean的方式返回List<Main_data>
        datas=Maindao.Main_queryall();
         main_adapter=new Main_adapter(this,datas);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(main_adapter);
        Add_shijian(add);
        Rede_shijian(rede);

    }
    //添加add点击事件
    public void Add_shijian(Button add){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFragment addFragment=new AddFragment();

                addFragment.show(getFragmentManager(),"addFragment");
            }
        });
    }
    //添加rede点击事件
    public  void Rede_shijian(Button rede){
        caozuo.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void noti() {
        List<Main_data> temp=Maindao.Main_queryall();
        datas.clear();
        datas.addAll(temp);
         main_adapter.notifyDataSetChanged();
    }
}
