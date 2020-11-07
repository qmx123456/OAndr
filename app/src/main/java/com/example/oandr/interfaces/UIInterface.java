package com.example.oandr.interfaces;

import android.view.View;

public interface UIInterface {
    int getLayoutId();
    void initView();
    void initData();
    void initListener();
    void processClick(View v);
}
