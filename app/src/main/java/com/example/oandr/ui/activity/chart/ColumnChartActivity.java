package com.example.oandr.ui.activity.chart;

import android.app.Activity;
import android.view.View;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class ColumnChartActivity extends BaseActivity {

    private ColumnChartView mColumnChartView;
    private boolean hasColumnLabels = false;
    private boolean hasLabelsOnlyForSelected = false;
    private boolean hasAxis = true;
    private boolean hasAxisLabel = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_column_chart;
    }

    @Override
    public void initView() {
        mColumnChartView = (ColumnChartView) findViewById(R.id.cvc_main);
    }

    @Override
    public void initData() {
        ArrayList<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        int numColumns = 8;
        int numSubColumns = 1;
        for (int i = 0; i< numColumns; i++){
            values = new ArrayList<>();
            for (int j = 0; j< numSubColumns; j++){
                values.add(new SubcolumnValue((float)Math.random()*50, ChartUtils.pickColor()));
            }
            Column column = new Column(values);

            column.setHasLabels(hasColumnLabels);
            column.setHasLabelsOnlyForSelected(hasLabelsOnlyForSelected);
            columns.add(column);
        }
        ColumnChartData columnChartData = new ColumnChartData(columns);
        columnChartData.setStacked(true);
        if(hasAxis){
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if(hasAxisLabel){
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            columnChartData.setAxisXBottom(axisX);
            columnChartData.setAxisYLeft(axisY);
        }else {
            columnChartData.setAxisXBottom(null);
            columnChartData.setAxisYLeft(null);
        }
        mColumnChartView.setColumnChartData(columnChartData);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }
}
