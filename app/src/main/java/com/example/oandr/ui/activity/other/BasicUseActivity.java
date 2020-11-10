package com.example.oandr.ui.activity.other;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;
import com.example.oandr.ui.activity.chart.LineChartActivity;

public class BasicUseActivity extends BaseActivity {

    private ImageView titileImage;
    private CardView lineCardView;
    private ImageView lineImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_basic_use;
    }

    @Override
    public void initView() {
        titileImage = (ImageView) findViewById(R.id.iv_basic_title);

        lineCardView = (CardView) findViewById(R.id.cv_line_chart);
        lineImage = (ImageView) findViewById(R.id.iv_line_chart);
    }
    @Override
    public void initData() {
//        Glide.with(this).load(R.mipmap.heng_3).into(titileImage);//加载图片，可省略
    }

    @Override
    public void initListener() {
        lineCardView.setOnClickListener(this);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.cv_line_chart:
                Intent i1 = new Intent(this, LineChartActivity.class);
                startActivity(i1, ActivityOptions.makeSceneTransitionAnimation(this, lineImage, "line").toBundle());
                break;
        }
    }
}
