package com.example.oandr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(0, 5f));
        values.add(new PointValue(1, 0.4f));
        values.add(new PointValue(2, 0.3f));
        values.add(new PointValue(3, 9));
        values.add(new PointValue(4, 5));
        values.add(new PointValue(5, 2));

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        line.setColor(Color.parseColor("#FFCD41"));//设置颜色,默认#DFDFDF,蓝色
        line.setShape(ValueShape.DIAMOND);//设置点的形状，默认是CIRCLE
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线，默认是true平滑拟合的
        line.setFilled(true);//是否填充曲线的面积，默认false不填充
//        line.setHasLabels(true);//曲线的数据坐标是否加上备注，默认false不显示备注
        line.setHasLabelsOnlyForSelected(true);//点击"点"时,显示坐标值,默认false不显示
        line.setHasLines(true);//是否连接成线,默认true连成线
        line.setHasPoints(true);//是否突显数据点,默认是true突显数据点

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        LineChartView chart = (LineChartView)this.findViewById(R.id.chart);
        chart.setLineChartData(data);
    }
}