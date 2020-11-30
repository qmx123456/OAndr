package com.example.oandr.ui.activity.other;

import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;
import com.example.oandr.ui.activity.usage.ExchangeActivity;

public class UsageActivity extends BaseActivity {

    private CardView exchangeCardView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_use_scene;
    }

    @Override
    public void initView() {
        exchangeCardView = (CardView) findViewById(R.id.cv_usage_exchange);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        exchangeCardView.setOnClickListener(this);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.cv_usage_exchange:
                Intent i1 = new Intent(this, ExchangeActivity.class);
                startActivity(i1);
                break;
        }
    }
}
