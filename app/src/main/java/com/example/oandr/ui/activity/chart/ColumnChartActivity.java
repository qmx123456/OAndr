package com.example.oandr.ui.activity.chart;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class ColumnChartActivity extends BaseActivity {

    private ColumnChartView mColumnChartView;
    private boolean hasColumnLabels = false;
    private boolean hasLabelsOnlyForSelected = false;
    private boolean hasAxis = true;
    private boolean hasAxisLabel = true;
    private boolean isValueSelectionEnabled = false;

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
        setColumnDatas(8, 1, false, false);
    }

    private void setColumnDatas(int numColumns, int numSubColumns, boolean isStacked, boolean isNegative) {
        ArrayList<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i< numColumns; i++){
            values = new ArrayList<>();
            for (int j = 0; j< numSubColumns; j++){
                values.add(new SubcolumnValue((float)Math.random()*50* getRandomNegative(isNegative), ChartUtils.pickColor()));
            }
            Column column = new Column(values);

            column.setHasLabels(hasColumnLabels);
            column.setHasLabelsOnlyForSelected(hasLabelsOnlyForSelected);
            columns.add(column);
        }
        ColumnChartData columnChartData = new ColumnChartData(columns);
        columnChartData.setStacked(isStacked);
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
        mColumnChartView.setValueSelectionEnabled(isValueSelectionEnabled);
    }

    private int getRandomNegative(boolean isNegative) {
        if(isNegative){
            int[] sign = {1, -1};
            return sign[(int)Math.round(Math.random())];
        }
        return 1;
    }

    @Override
    public void initListener() {
        mColumnChartView.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                Toast.makeText(ColumnChartActivity.this, "当前列的值约为："+(int)value.getValue(), Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.menu_column_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_column_reset:
                resetColumnDatas();
                return true;
            case R.id.menu_column_subcolumn:
                setColumnDatas(8,4, false, false);return true;
            case R.id.menu_column_negative_subcolumn:
                setColumnDatas(8,4,false, true);return true;
            case R.id.menu_column_stack:
                setColumnDatas(8,4,true,false);return true;
            case R.id.menu_column_negative_stack:
                setColumnDatas(8,4,true,true);return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetColumnDatas() {
        hasColumnLabels = false;
        hasLabelsOnlyForSelected = false;
        hasAxis = true;
        hasAxisLabel = true;
        isValueSelectionEnabled = false;
        setColumnDatas(8, 1, false, false);
    }
}
