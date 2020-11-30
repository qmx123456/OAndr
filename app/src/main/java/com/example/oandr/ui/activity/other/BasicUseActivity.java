package com.example.oandr.ui.activity.other;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;
import com.example.oandr.ui.activity.chart.BubbleChartActivity;
import com.example.oandr.ui.activity.chart.ColumnChartActivity;
import com.example.oandr.ui.activity.chart.LineChartActivity;
import com.example.oandr.ui.activity.chart.PieChartActivity;

public class BasicUseActivity extends BaseActivity {

    private ImageView titileImage;
    private CardView lineCardView;
    private ImageView lineImage;
    private ImageView columnImage;
    private CardView columnCardView;
    private CardView pieCardView;
    private ImageView pieImage;
    private CardView bubbleCardView;
    private ImageView bubbleImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_basic_use;
    }

    @Override
    public void initView() {
        titileImage = (ImageView) findViewById(R.id.iv_basic_title);

        lineCardView = (CardView) findViewById(R.id.cv_line_chart);
        lineImage = (ImageView) findViewById(R.id.iv_line_chart);

        columnCardView = (CardView) findViewById(R.id.cv_column_chart);
        columnImage = (ImageView) findViewById(R.id.iv_column_chart);

        pieCardView = (CardView) findViewById(R.id.cv_pie_chart);
        pieImage = (ImageView) findViewById(R.id.iv_pie_chart);

        bubbleCardView = (CardView) findViewById(R.id.cv_bubble_chart);
        bubbleImage = (ImageView) findViewById(R.id.iv_bubble_chart);
    }
    @Override
    public void initData() {
//        Glide.with(this).load(R.mipmap.heng_3).into(titileImage);//加载图片，可省略
    }

    @Override
    public void initListener() {
        lineCardView.setOnClickListener(this);
        columnCardView.setOnClickListener(this);
        pieCardView.setOnClickListener(this);
        bubbleCardView.setOnClickListener(this);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.cv_line_chart:
                Intent i1 = new Intent(this, LineChartActivity.class);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, lineImage, getString(R.string.line));
                startActivity(i1, activityOptions.toBundle());
                break;
            case  R.id.cv_column_chart:
                Intent i2 = new Intent(this, ColumnChartActivity.class);
                ActivityOptions activityOptions1 = ActivityOptions.makeSceneTransitionAnimation(this, columnImage, getString(R.string.column));
                startActivity(i2, activityOptions1.toBundle());
                break;
            case R.id.cv_pie_chart:
                Intent i3 = new Intent(this, PieChartActivity.class);
                ActivityOptions activityOptions2 = ActivityOptions.makeSceneTransitionAnimation(this, pieImage, getString(R.string.pie));
                startActivity(i3, activityOptions2.toBundle());
                break;
            case R.id.cv_bubble_chart:
                Intent i4 = new Intent(this, BubbleChartActivity.class);
                ActivityOptions activityOptions3 = ActivityOptions.makeSceneTransitionAnimation(this, bubbleImage, getString(R.string.bubble));
                startActivity(i4, activityOptions3.toBundle());
                break;
        }
    }
}
