package com.example.oandr.ui.activity.chart;

import android.view.View;
import android.widget.Toast;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChartActivity extends BaseActivity {

    private PieChartView mPieChartView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pie_chart;
    }

    @Override
    public void initView() {
        mPieChartView = (PieChartView) findViewById(R.id.pvc_main);
    }

    @Override
    public void initData() {
        int numValues = 6;
        ArrayList<SliceValue> sliceValues = new ArrayList<>();
        for (int i=0;i<numValues;i++){
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30, ChartUtils.pickColor());
            sliceValues.add(sliceValue);
        }
        PieChartData pieChartData = new PieChartData(sliceValues);
        mPieChartView.setPieChartData(pieChartData);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }
}
