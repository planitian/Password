package com.example.administrator.password.Fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.password.Adapter.Main_adapter;
import com.example.administrator.password.Bean.Main_data;
import com.example.administrator.password.Dao.Maindao;
import com.example.administrator.password.R;

/**
 * Created by Administrator on 2018\5\21 0021.
 */

public class AddFragment extends DialogFragment {
    private View view;
    private Context context;
    private EditText leixing;
    private EditText zhanghao;
    private EditText password;
    private Button quxiao;
    private Button queding;
    private AddCallback addCallback;

    public AddFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addxinxi, container, false);
        leixing = view.findViewById(R.id.add_leixing);
        zhanghao = view.findViewById(R.id.add_zhanghao);
        password = view.findViewById(R.id.add_password);
        quxiao = view.findViewById(R.id.Label_quxiao);
        queding = view.findViewById(R.id.Label_queding);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFragment.this.dismiss();
            }
        });
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lei = leixing.getText().toString().trim();
                String zhang = zhanghao.getText().toString().trim();
                String mi = password.getText().toString().trim();
                Main_data main_data = new Main_data(lei, zhang, mi);
                long result = Maindao.Main_insert(main_data);
                if (result != -1) {
                    Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
                    AddFragment.this.dismiss();
                    addCallback.noti();

                } else {
                    Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_SHORT).show();
                    AddFragment.this.dismiss();
                }
//                Maindao.Main_inserttest(main_data);
//                AddFragment.this.dismiss();
//                addCallback.noti();
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof AddCallback) {
            addCallback = (AddCallback) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.dimAmount = 0;
        window.setAttributes(layoutParams);
    }

    public interface AddCallback {
        void noti();
    }
}
