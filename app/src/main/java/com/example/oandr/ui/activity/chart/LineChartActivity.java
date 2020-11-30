package com.example.oandr.ui.activity.chart;

import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.gesture.ZoomType;
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

    //数据
    private LineChartData mLineData;
    private int numberOfPoints = 12;
    private int maxNumberOfLines = 4;
    private float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private int numberOfLines = 1;
    //属性
    private boolean doesHaveLine = true;
    private boolean isCubic = false;
    private boolean isFilled = false;

    private boolean doesHavePoints = true;
    private ValueShape pointsShape = ValueShape.CIRCLE;
    private boolean doesHavePointLabels = false;
    private boolean doesPointHaveLabelForSelected = true;

    private boolean doPointsHaveDifferentColor = false;
    private boolean doesHaveAxes = true;
    private boolean doesHaveAxisNames = true;
    private boolean enableValueSelection = false;

    private boolean enableZoom = true;
    private ZoomType zoomType = ZoomType.HORIZONTAL_AND_VERTICAL;

    Timer timer = new Timer();
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
        for (int i = 0; i < numberOfLines; i++) {
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
            line.setHasLabelsOnlyForSelected(doesPointHaveLabelForSelected);
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
        mLineChartView.setValueSelectionEnabled(enableValueSelection);
        mLineChartView.setZoomEnabled(enableZoom);
        mLineChartView.setZoomType(zoomType);
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
                baseText("选中第" + (lineIndex + 1) + "条线，第" + (1 + pointIndex) + "个节点\r\n" + "其坐标为(" + value.getX() + ", " + value.getY() + ")");
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
            case R.id.menu_line_add:addLine();return true;
            case R.id.menu_line_hide_show_lines: showOrHideLines(); return true;
            case R.id.menu_line_hide_show_points: showOrHidePoints(); return true;
            case R.id.menu_line_hide_show_point_labels: showOrHidePointLabels(); return true;
            case R.id.menu_line_chart_hide_show_axis: showOrHideAxis(); return true;
            case R.id.menu_line_chart_hide_show_axis_name: showOrHideAxisName(); return true;
            case R.id.menu_line_cubic:changeCubicLine(); return true;
            case R.id.menu_line_fill_area: fillLineArea(); return true;
            case R.id.menu_line_different_points_color: differentPointsColor(); return true;
            case R.id.menu_line_circle_point: circlePoint(); return true;
            case R.id.menu_line_square_point: squarePoint(); return true;
            case R.id.menu_line_diamond_point: diamondPoint(); return true;
            case R.id.menu_animation: animation(); return true;
            case R.id.menu_line_select_show_label: showLabelForSelect(); return true;
            case R.id.menu_view_touch_zoom: enableZoom =!enableZoom;mLineChartView.setZoomEnabled(enableZoom);return true;
            case R.id.menu_view_touch_zoom_xy:zoomType = ZoomType.HORIZONTAL_AND_VERTICAL;mLineChartView.setZoomType(zoomType);return true;
            case R.id.menu_view_touch_zoom_x:zoomType = ZoomType.HORIZONTAL;mLineChartView.setZoomType(zoomType);return true;
            case R.id.menu_view_touch_zoom_y:zoomType = ZoomType.VERTICAL; mLineChartView.setZoomType(zoomType);return true;
            case R.id.menu_view_dinamic_graph:dynamicDataDisplay();return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFinish = false;
    private int position = 0;
    private List<PointValue> pointValueList = new ArrayList<>();

    private void dynamicDataDisplay() {
        resetTimer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isFinish){
                    PointValue value = new PointValue(position * 5, (float) Math.random() * 100);
                    pointValueList.add(value);

                    float x = value.getX();

                    Line line = new Line(pointValueList);
                    line.setColor(Color.RED);
                    line.setShape(ValueShape.CIRCLE);
                    line.setCubic(true);

                    List<Line> lineList = new ArrayList<>();
                    lineList.add(line);

                    LineChartData lineChartData = new LineChartData();
                    lineChartData.setLines(lineList);
                    Axis axisX = new Axis();
                    axisX.setName("Axis X");
                    axisX.setTextColor(Color.GRAY);
                    lineChartData.setAxisXBottom(axisX);

                    Axis axisY = new Axis().setHasLines(true);
                    axisY.setName("Axis Y");
                    axisY.setTextColor(Color.GRAY);
                    lineChartData.setAxisYLeft(axisY);

                    mLineChartView.setLineChartData(lineChartData);

                    Viewport viewport = new Viewport();
                    viewport.top = 100;
                    viewport.bottom = 0;
                    viewport.left = 0;
                    viewport.right = 50 ;
                    if(x > 50){
                        viewport.left = x-50;
                        viewport.right = x ;
                    }

                    mLineChartView.setCurrentViewport(viewport);

                    Viewport viewport1 = new Viewport();
                    viewport1.top = 100;
                    viewport1.bottom = 0 ;
                    viewport1.left = 0;
                    viewport1.right = x + 50;
                    mLineChartView.setMaximumViewport(viewport1);

                    position++;
                    if (position > 100){
                        isFinish = true;
//                        mLineChartView.setInteractive(true);
                    }
                }
            }
        }, 300, 300);
    }

    private void resetTimer() {
        if (timer != null){
            timer.cancel();
            timer = new Timer();
        }
        isFinish = false;
        pointValueList.clear();
        position = 0;
//        mLineChartView.setInteractive(true);
    }

    private void showLabelForSelect() {
        doesPointHaveLabelForSelected = !doesPointHaveLabelForSelected;
        enableValueSelection = doesPointHaveLabelForSelected;
        if (doesPointHaveLabelForSelected){
            doesHavePointLabels = false;
        }
        setLinesDatas();
    }

    private void animation() {
        for(Line line: mLineData.getLines()){
            for(PointValue value: line.getValues()){
                value.setTarget(value.getX(), (float)Math.random()*100);
            }
        }
        mLineChartView.startDataAnimation();
    }

    private void diamondPoint() {
        pointsShape = ValueShape.DIAMOND;
        setLinesDatas();
    }

    private void squarePoint() {
        pointsShape = ValueShape.SQUARE;
        setLinesDatas();
    }

    private void circlePoint() {
        pointsShape = ValueShape.CIRCLE;
        setLinesDatas();
    }

    private void differentPointsColor() {
        doPointsHaveDifferentColor = !doPointsHaveDifferentColor;
        setLinesDatas();
    }

    private void fillLineArea() {
        isFilled = !isFilled;
        setLinesDatas();
    }

    private void changeCubicLine() {
        isCubic = !isCubic;
        setLinesDatas();
    }

    private void showOrHideAxisName() {
        doesHaveAxisNames = !doesHaveAxisNames;
        setLinesDatas();
    }

    private void showOrHideAxis() {
        doesHaveAxes = !doesHaveAxes;
        setLinesDatas();
    }

    private void showOrHidePointLabels() {
        doesHavePointLabels = !doesHavePointLabels;
        doesPointHaveLabelForSelected = ! doesHavePointLabels;
        setLinesDatas();
    }

    private void showOrHidePoints() {
        doesHavePoints = !doesHavePoints;
        setLinesDatas();
    }

    private void showOrHideLines() {
        doesHaveLine = !doesHaveLine;
        setLinesDatas();
    }

    private void addLine() {
        if(numberOfLines>= maxNumberOfLines){
            Toast.makeText(this, "最多只有4条线",Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfLines++;
        setLinesDatas();
    }

    private void resetLines() {
        numberOfLines = 1;

        doesHaveLine = true;
        isCubic = false;
        isFilled = false;

        doesHavePoints = true;
        pointsShape = ValueShape.CIRCLE;
        doesHavePointLabels = false;
        doesPointHaveLabelForSelected = true;

        doesHaveAxes = true;
        doesHaveAxisNames = true;
        doPointsHaveDifferentColor = false;

        enableValueSelection = false;
        enableZoom = true;
        zoomType =  ZoomType.HORIZONTAL_AND_VERTICAL;

        resetTimer();
        setLinesDatas();
        resetViewport();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_line_chart, menu);
        return true;
    }
}
