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
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(0, 0.2f));
        values.add(new PointValue(1, 0.4f));
        values.add(new PointValue(2, 0.3f));
        values.add(new PointValue(3, 9));
        values.add(new PointValue(4, 5));
        values.add(new PointValue(5, 2));

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        LineChartView chart = (LineChartView)this.findViewById(R.id.chart);
        chart.setLineChartData(data);
    }
}