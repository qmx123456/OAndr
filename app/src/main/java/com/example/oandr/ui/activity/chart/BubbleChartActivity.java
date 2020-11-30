package com.example.oandr.ui.activity.chart;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;

public class BubbleChartActivity extends BaseActivity {

    private boolean isZoomEnabled = true;
    private ZoomType zoomType = ZoomType.HORIZONTAL_AND_VERTICAL;
    private boolean isValueSelectionEnabled = false;
    private boolean hasAxis = true;
    private boolean hasAxisName = true;
    private boolean hasLabelsOnlyForSelected = false;
    private boolean hasLabels = false;
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
        setBubbleData();
    }

    private void setBubbleData() {
        List<BubbleValue> values = new ArrayList<BubbleValue>();
        for(int i=0; i<BUBBLES_NUM; i++){
            BubbleValue value = new BubbleValue(i, (float) Math.random() * 10, (float) Math.random() * 1000);
            value.setColor(ChartUtils.pickColor());
            value.setShape(bubbleShape);
            values.add(value);
        }

        BubbleChartData mbubbleChartData = new BubbleChartData(values);
        mbubbleChartData.setHasLabels(hasLabels);
        mbubbleChartData.setHasLabelsOnlyForSelected(hasLabelsOnlyForSelected);
        if(hasAxis){
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if(hasAxisName){
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            mbubbleChartData.setAxisXBottom(axisX);
            mbubbleChartData.setAxisYLeft(axisY);
        }else {
            mbubbleChartData.setAxisXBottom(null);
            mbubbleChartData.setAxisYLeft(null);
        }

        mBubbleView.setBubbleChartData(mbubbleChartData);
        mBubbleView.setValueSelectionEnabled(isValueSelectionEnabled);
        mBubbleView.setZoomEnabled(isZoomEnabled);
        mBubbleView.setZoomType(zoomType);
    }

    @Override
    public void initListener() {
        mBubbleView.setOnValueTouchListener(new BubbleChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int bubbleIndex, BubbleValue value) {
                baseText("第"+(bubbleIndex+1)+"个泡泡，其值约为：("+value.getX()+", "+value.getY()+", "+value.getZ()+")");
            }

            @Override
            public void onValueDeselected() {

            }
        });
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bubble_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_bubble_reset:reset();
                return true;
            case R.id.menu_bubble_style_circle:
                bubbleShape = ValueShape.CIRCLE;setBubbleData();
                return true;
            case R.id.menu_bubble_style_tangle:
                bubbleShape=ValueShape.SQUARE;setBubbleData();
                return true;
            case R.id.menu_bubble_show_label:
                hasLabels = !hasLabels;
                if(hasLabels){
                    hasLabelsOnlyForSelected = false;
                    isValueSelectionEnabled = false;
                }
                setBubbleData();
                return true;
            case R.id.menu_bubble_show_axis:
                hasAxis = !hasAxis;
                setBubbleData();
                return true;
            case R.id.menu_bubble_show_axis_name:
                hasAxisName = !hasAxisName;
                setBubbleData();
                return true;
            case R.id.menu_bubble_dynamic:
                dynamic();
                return true;
            case R.id.menu_bubble_click_show_label:
                hasLabelsOnlyForSelected = !hasLabelsOnlyForSelected;
                isValueSelectionEnabled = hasLabelsOnlyForSelected;
                if (hasLabelsOnlyForSelected){
                    hasLabels = false;
                }
                setBubbleData();
                return true;
            case R.id.menu_bubble_zoom:
                mBubbleView.setZoomEnabled(!mBubbleView.isZoomEnabled());
                return true;
            case R.id.menu_bubble_zoom_XY:
                mBubbleView.setZoomEnabled(true);
                mBubbleView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
                return true;
            case R.id.menu_bubble_zoom_X:
                mBubbleView.setZoomEnabled(true);
                mBubbleView.setZoomType(ZoomType.HORIZONTAL);
                return true;
            case R.id.menu_bubble_zoom_Y:
                mBubbleView.setZoomEnabled(true);
                mBubbleView.setZoomType(ZoomType.VERTICAL);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dynamic() {
        BubbleChartData bubbleChartData = mBubbleView.getBubbleChartData();
        for (BubbleValue value : bubbleChartData.getValues()){
            value.setTarget(value.getX(), (float)Math.random()*100, (float)Math.random()*1000);
        }
        mBubbleView.startDataAnimation();
    }

    private void reset() {
        hasAxis = true;
        hasAxisName = true;
        hasLabelsOnlyForSelected = false;
        hasLabels = false;
        bubbleShape = ValueShape.CIRCLE;
        isZoomEnabled = true;
        zoomType = ZoomType.HORIZONTAL_AND_VERTICAL;
        setBubbleData();
    }
}
