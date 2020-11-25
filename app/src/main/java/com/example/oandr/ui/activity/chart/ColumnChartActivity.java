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
    private boolean isStacked = false;
    private boolean isNegative = false;
    private int numColumns = 8;
    private int numSubColumns  = 1;

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
        setColumnDatas();
    }

    private void setColumnDatas() {
        ArrayList<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i< this.numColumns; i++){
            values = new ArrayList<>();
            for (int j = 0; j< this.numSubColumns; j++){
                values.add(new SubcolumnValue((float)Math.random()*50* getRandomNegative(this.isNegative), ChartUtils.pickColor()));
            }
            Column column = new Column(values);

            column.setHasLabels(hasColumnLabels);
            column.setHasLabelsOnlyForSelected(hasLabelsOnlyForSelected);
            columns.add(column);
        }
        ColumnChartData columnChartData = new ColumnChartData(columns);
        columnChartData.setStacked(this.isStacked);
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
                Toast.makeText(ColumnChartActivity.this, "第"+String.valueOf(columnIndex+1)+"列，第"
                        +String.valueOf(subcolumnIndex+1)+"子列，\r\n其值约为："+(int)value.getValue(), Toast.LENGTH_SHORT).show();
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
                this.isStacked = false;
                this.isNegative = false;
                this.numColumns = 8;
                this.numSubColumns =4;
                setColumnDatas();return true;
            case R.id.menu_column_negative_subcolumn:
                this.isStacked = false;
                this.isNegative = true;
                this.numColumns = 8;
                this.numSubColumns =4;
                setColumnDatas();return true;
            case R.id.menu_column_stack:
                this.isStacked = true;
                this.isNegative = false;
                this.numColumns = 8;
                this.numSubColumns =4;
                setColumnDatas();return true;
            case R.id.menu_column_negative_stack:
                this.isStacked = true;
                this.isNegative = true;
                this.numColumns = 8;
                this.numSubColumns =4;
                setColumnDatas();return true;
            case R.id.menu_column_show_label:
                hasColumnLabels = !hasColumnLabels;
                setColumnDatas();return true;
            case R.id.menu_column_show_axis:
                hasAxis = !hasAxis;
                setColumnDatas();
                return true;
            case R.id.menu_column_show_axis_name:
                hasAxisLabel = !hasAxisLabel;
                setColumnDatas();
                return true;
            case R.id.menu_column_animation:
                changeColumnsAnimate();return true;
            case R.id.menu_column_show_column_label:
                hasLabelsOnlyForSelected =!hasLabelsOnlyForSelected;
                if (hasLabelsOnlyForSelected){
                    hasColumnLabels = false;
                }
                isValueSelectionEnabled = !isValueSelectionEnabled;
                setColumnDatas();return true;
            case R.id.menu_column_touch_zoom:
                mColumnChartView.setZoomEnabled(!mColumnChartView.isZoomEnabled());return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void changeColumnsAnimate() {
        ColumnChartData columnChartData = mColumnChartView.getColumnChartData();
        for (Column column : columnChartData.getColumns()){
            for(SubcolumnValue subcolumnValue:column.getValues()){
                subcolumnValue.setTarget((float)Math.random()*100*getRandomNegative(isNegative));
            }
        }
        mColumnChartView.startDataAnimation();
    }

    private void resetColumnDatas() {
        hasColumnLabels = false;
        hasLabelsOnlyForSelected = false;
        hasAxis = true;
        hasAxisLabel = true;
        isValueSelectionEnabled = false;
        isNegative = false;
        isStacked = false;
        this.numColumns = 8;
        this.numSubColumns =1;
        setColumnDatas();
    }
}
