package com.example.oandr.ui.activity.other;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private CardView useageCardView;
    private CardView basicCard;
    private ImageView basicImage;
    private ImageView usageImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        basicCard = findViewById(R.id.cv_main_basic);
        basicImage = findViewById(R.id.iv_main_basic);

        useageCardView = (CardView) findViewById(R.id.cv_main_usage);
        usageImage = (ImageView) findViewById(R.id.iv_main_useage);
    }

    @Override
    public void initData() {
//        Glide.with(this).load(R.mipmap.heng_3).into(basicImage);//加载图片，可省略
    }

    @Override
    public void initListener() {
        basicCard.setOnClickListener(this);
        useageCardView.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)//要求api至少为21
    @Override
    public void processClick(View v) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            switch (v.getId()){
                case R.id.cv_main_basic:
                    Intent i1 = new Intent(this, BasicUseActivity.class);
                    ActivityOptions activityOptions1 = ActivityOptions.makeSceneTransitionAnimation(this, basicCard, this.getString(R.string.basic));
                    startActivity(i1, activityOptions1.toBundle());
                    break;
                case R.id.cv_main_usage:
                    Intent i2 = new Intent(this, UsageActivity.class);
                    ActivityOptions activityOptions2 = ActivityOptions.makeSceneTransitionAnimation(this, useageCardView, getString(R.string.usage));
                    startActivity(i2, activityOptions2.toBundle());
                    break;
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("版本过低");
            builder.setMessage("版本太低，系统暂不支持!");
            builder.show();
        }
    }
}
