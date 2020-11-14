package com.example.oandr.ui.activity.chart;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

import java.util.ArrayList;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class LineChartActivity extends BaseActivity {
    private LineChartView mLineChartView;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;
    private float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
    private ValueShape pointsShape = ValueShape.CIRCLE;
    private boolean doesHaveLine = true;
    private boolean doesHavePoints = true;
    private boolean isCubic = true;
    private boolean isFilled = false;
    private boolean doesHavePointLabels = false;
    private boolean doesPointHaveSelected = true;
    private boolean doPointsHaveDifferentColor = false;
    private LineChartData mLineData;
    private boolean doesHaveAxes = true;
    private boolean doesHaveAxisNames = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_line_chart;
    }

    @Override
    public void initView() {
        mLineChartView = (LineChartView) findViewById(R.id.lvc_main);
        mLineChartView.setViewportCalculationEnabled(false);//禁用视图重新计算，动态更改-待查证
    }

    @Override
    public void initData() {
        initPointValues();
        setLinesDatas();
        resetViewport();
    }

    private void resetViewport() {
        Viewport viewport = new Viewport(mLineChartView.getMaximumViewport());
        viewport.left =0;
        viewport.bottom=0;
        viewport.top=100;
        viewport.right = numberOfPoints - 1;
        mLineChartView.setMaximumViewport(viewport);
        mLineChartView.setCurrentViewport(viewport);
    }

    private void setLinesDatas() {
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < maxNumberOfLines; i++) {
            ArrayList<PointValue> values = new ArrayList<>();
            for (int j = 0; j < numberOfPoints; j++) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(pointsShape);
            line.setHasLines(doesHaveLine);
            line.setHasPoints(doesHavePoints);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(doesHavePointLabels);
            line.setHasLabelsOnlyForSelected(doesPointHaveSelected);
            if (doPointsHaveDifferentColor) {
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }
        mLineData = new LineChartData(lines);
        mLineData.setBaseValue(Float.NEGATIVE_INFINITY);//奇怪的设置
        /* 其他的属性方法
        mLineData.setValueLabelBackgroundAuto(false);
        mLineData.setValueLabelBackgroundColor(Color.BLUE);
        mLineData.setValueLabelBackgroundEnabled(true);
        mLineData.setValueLabelsTextColor(Color.RED);
        mLineData.setValueLabelTextSize(30);
        mLineData.setValueLabelTypeface(Typeface.MONOSPACE);
        */
        if(doesHaveAxes){
            Axis axisX = new Axis();
            axisX.setTextColor(Color.GRAY);
            Axis axisY = new Axis().setHasLines(true);
            axisY.setTextColor(Color.GRAY);

            if(doesHaveAxisNames){
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            mLineData.setAxisXBottom(axisX);
            mLineData.setAxisYLeft(axisY);
        } else{
            mLineData.setAxisXBottom(null);
            mLineData.setAxisYLeft(null);
        }

        mLineChartView.setLineChartData(mLineData);
    }

    private void initPointValues() {
        for (int i = 0; i < maxNumberOfLines; i++) {
            for (int j = 0; j < numberOfPoints; j++) {
                randomNumbersTab[i][j] = (float) Math.random()*100;
            }
        }
    }

    @Override
    public void initListener() {
        mLineChartView.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(LineChartActivity.this,
                        "选中第"+(lineIndex+1)+"条线，第"+(1+pointIndex)+"个节点\r\n"+"其坐标为("+value.getX()+", "+value.getY()+")",
                        Toast.LENGTH_LONG).show();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_line_reset: resetLines(); return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetLines() {
        maxNumberOfLines = 1;
        doesHaveAxisNames = true;
        doesHaveLine = true;
        doesHavePoints = true;
        pointsShape = ValueShape.CIRCLE;
        isFilled = false;
        doesHavePointLabels = false;
        isCubic = false;
        doesPointHaveSelected = false;
        doPointsHaveDifferentColor = false;

        mLineChartView.setInteractive(true);
//        position = 0 ;
//        pointValueList.clear();
//        lineChartData.clear();

//        if(timer != null){
//            timer.cancel();
//            timer = new Timer();
//        }

        mLineChartView.setValueSelectionEnabled(doesPointHaveSelected);
        resetViewport();
        setLinesDatas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_line_chart, menu);
        return true;
    }
}
