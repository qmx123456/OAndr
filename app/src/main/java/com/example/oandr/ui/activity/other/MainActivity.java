package com.example.oandr.ui.activity.other;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private CardView basicCard;
    private ImageView basicImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        basicCard = findViewById(R.id.cv_main_basic);
        basicImage = findViewById(R.id.iv_main_basic);
    }

    @Override
    public void initData() {
        Glide.with(this).load(R.mipmap.heng_3).into(basicImage);
    }

    @Override
    public void initListener() {
        basicCard.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.cv_main_basic:
                Intent i1 = new Intent(this, BasicUseActivity.class);
                startActivity(i1, ActivityOptions.makeSceneTransitionAnimation(this, basicCard,"basic").toBundle());
                break;
        }
    }
}
