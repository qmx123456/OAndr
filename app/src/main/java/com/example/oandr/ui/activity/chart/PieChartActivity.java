package com.example.oandr.ui.activity.chart;

import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChartActivity extends BaseActivity {

    private boolean isValueSelectionEnabled = false;
    private boolean hasLabelsOnlyForSelected = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean isExploded = false;
    private boolean isHasCenterSingleText = false;
    private boolean isHasCenterDoubleText = false;
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
        if(hasLabelsOutside){
            mPieChartView.setCircleFillRatio(0.7f);
        }else {
            mPieChartView.setCircleFillRatio(1.0f);
        }
        mPieChartView.setValueSelectionEnabled(isValueSelectionEnabled);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pie_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_pie_reset:resetPieChart();return true;
            case R.id.menu_pie_explode:
                isExploded = !isExploded;
                setPieChartData();
                return true;
            case R.id.menu_pie_circle:
                hasCenterCircle = !hasCenterCircle;
                setPieChartData();return true;
            case R.id.menu_pie_circle_single_text:
                hasCenterCircle = true;
                isHasCenterDoubleText = false;
                isHasCenterSingleText = !isHasCenterSingleText;
                setPieChartData();return true;
            case R.id.menu_pie_circle_double_text:
                hasCenterCircle = true;
                isHasCenterSingleText = true;
                isHasCenterDoubleText = !isHasCenterDoubleText;
                setPieChartData();return true;
            case R.id.menu_pie_inside_label:
                hasLabels = !hasLabels;
                hasLabelsOutside = false;
                hasLabelsOnlyForSelected = false;
                setPieChartData();return true;
            case R.id.menu_pie_outside_label:
                hasLabels = !hasLabels;
                hasLabelsOutside = hasLabels;
                if (hasLabels){
                    hasLabelsOnlyForSelected = false;
                    isValueSelectionEnabled = false;
                }
                setPieChartData();return true;
            case R.id.menu_pie_animation:
                pieAnimation();return true;
            case R.id.menu_pie_show_label:
                hasLabelsOnlyForSelected = !hasLabelsOnlyForSelected;
                isValueSelectionEnabled = hasLabelsOnlyForSelected;
                if (hasLabelsOnlyForSelected){
                    hasLabels = false;
                }
                setPieChartData();return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pieAnimation() {
        PieChartData pieChartData = mPieChartView.getPieChartData();
        for (SliceValue value: pieChartData.getValues()){
            value.setTarget((float)Math.random()*30+15);
        }
        mPieChartView.startDataAnimation();
    }

    private void resetPieChart() {
        isExploded = false;
        isHasCenterDoubleText = false;
        isHasCenterSingleText = false;
        hasCenterCircle = false;
        hasLabels = false;
        hasLabelsOnlyForSelected = false;
        hasLabelsOutside = false;
        setPieChartData();
    }
}
