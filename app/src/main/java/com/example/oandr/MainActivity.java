package com.example.oandr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
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
        line.setHasLabels(true);//曲线的数据坐标是否加上备注，默认false不显示备注
//        line.setHasLabelsOnlyForSelected(true);//点击"点"时,显示坐标值,默认false不显示
        line.setHasLines(true);//是否连接成线,默认true连成线
        line.setHasPoints(true);//是否突显数据点,默认是true突显数据点

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        //TODO 反思整理:线条的属性,并完善线条的相关属性
        //TODO 反思整理,坐标应该具备哪些属性,及其原因,建立线性图形,完整的属性模型
        //TODO 弄清,各个属性的默认值,及其在绘制过程的应用
        //TODO 待处理缺陷:坐标数值太小，显示不全，仅仅显示整数
        //TODO 待处理缺陷:平滑连线,会让部分线条离开显示区,出现断线的现象
        //TODO 增加鼠标动作
        //TODO 如何生成gradle包，方便导入自定义绘图包
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，默认false直的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色,默认Color.LTGRAY
        axisX.setName("x轴");  //坐标轴名称
        axisX.setTextSize(10);//设置字体(刻度\坐标轴名称)大小,默认12
//      axisX.setMaxLabelChars(8);//设置最多的刻度数,默认是?,可能是绘制的时候确定的,需要验证
        axisX.setHasLines(true); //x 轴分割线,默认false不显示分割线
        data.setAxisXTop(axisX);  //x 轴在顶部
        data.setAxisXBottom(axisX); //x 轴在底部

        Axis axisY = new Axis();//Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setHasLines(true);
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        data.setAxisYRight(axisY);  //y轴设置在右边


        LineChartView lineChart = (LineChartView)this.findViewById(R.id.chart);

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setVisibility(View.VISIBLE);
        lineChart.setLineChartData(data);

        lineChart.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(MainActivity.this, ""+value.getY(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });
        lineChart.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
    }
}