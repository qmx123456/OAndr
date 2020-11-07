package com.example.oandr.ui.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oandr.interfaces.UIInterface;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, UIInterface {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        initListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:processClick(v);break;
        }
    }

    protected void basetToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    protected void baseToast(int msgId){
        Toast.makeText(this, msgId, Toast.LENGTH_SHORT).show();
    }
}
