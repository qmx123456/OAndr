package com.example.oandr.ui.activity.chart;

import android.graphics.Typeface;
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

    private final boolean hasLabelsOnlyForSelected = false;
    private final boolean hasLabelsOutside = false;
    private final boolean hasCenterCircle = false;
    private final boolean isExploded = false;
    private final boolean isHasCenterSingleText = false;
    private final boolean isHasCenterDoubleText = false;
    private PieChartView mPieChartView;
    private boolean hasLabels = false;

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
        setPieChartData();
    }

    private void setPieChartData() {
        int numValues = 6;
        ArrayList<SliceValue> sliceValues = new ArrayList<>();
        for (int i=0;i<numValues;i++){
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30, ChartUtils.pickColor());
            sliceValues.add(sliceValue);
        }
        PieChartData pieChartData = new PieChartData(sliceValues);
        pieChartData.setHasLabels(hasLabels);
        pieChartData.setHasLabelsOnlyForSelected(hasLabelsOnlyForSelected);
        pieChartData.setHasLabelsOutside(hasLabelsOutside);
        pieChartData.setHasCenterCircle(hasCenterCircle);
        if(isExploded){
            pieChartData.setSlicesSpacing(18);
        }
        if (isHasCenterSingleText){
            pieChartData.setCenterText1("title");
        }
        if (isHasCenterDoubleText){
            pieChartData.setCenterText2("details");

            Typeface tf = Typeface.createFromAsset(this.getAssets()
                    , "Roboto-Italic.ttf");
            pieChartData.setCenterText2Typeface(tf);
            pieChartData.setCenterText2FontSize(ChartUtils.px2dp(getResources().getDisplayMetrics().scaledDensity,
                    (int)getResources().getDimension(R.dimen.pie_chart_double_text_size)));
        }

        mPieChartView.setPieChartData(pieChartData);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }
}
