package com.example.oandr.ui.activity.chart;

import android.view.View;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;

public class BubbleChartActivity extends BaseActivity {

    private ValueShape bubbleShape = ValueShape.CIRCLE;

    private static final int BUBBLES_NUM = 8;
    private BubbleChartView mBubbleView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bubble_chart;
    }

    @Override
    public void initView() {
        mBubbleView = (BubbleChartView) findViewById(R.id.bcv_main);
    }

    @Override
    public void initData() {
        List<BubbleValue> values = new ArrayList<BubbleValue>();
        for(int i=0; i<BUBBLES_NUM; i++){
            BubbleValue value = new BubbleValue(i, (float) Math.random() * 100, (float) Math.random() * 1000);
            value.setColor(ChartUtils.pickColor());
            value.setShape(bubbleShape);
            values.add(value);
        }

        BubbleChartData mbubbleChartData = new BubbleChartData(values);
        mBubbleView.setBubbleChartData(mbubbleChartData);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }
}
