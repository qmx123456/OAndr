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


//        getSupportActionBar().hide();//隐藏标题栏
        titileImage = (ImageView) findViewById(R.id.iv_basic_title);

        lineCardView = (CardView) findViewById(R.id.cv_line_chart);
        lineImage = (ImageView) findViewById(R.id.iv_line_chart);
    }
    protected void hideUIMenu() {
        //去掉系统自带的导航栏，隐藏虚拟按键，并且全屏
        View decorView = this.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            decorView.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;

            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    @Override
    public void initData() {
        Glide.with(this).load(R.mipmap.heng_3).into(titileImage);
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
