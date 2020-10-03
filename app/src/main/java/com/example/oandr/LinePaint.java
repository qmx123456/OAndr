package com.example.oandr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.qmx.draw.AbsAxis;
import com.qmx.draw.XAxis;
import com.qmx.draw.YAxis;

public class LinePaint extends View {

    private float width;
    private float height;
    private float[] x = new float[]{1,2,3,4,5};
    private float[] y = new float[]{1,2,3,4,5};
    private  float leftDpTpPx;
    private float rightDpToPx;
    private float topDpToPx;
    private float bottomDpToPx;

    public LinePaint(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setBackgroundColor(Color.WHITE);
        initFields(context);
    }

    private void initFields(Context context) {
        Resources resources = this.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float density = displayMetrics.density;

        leftDpTpPx = context.getResources().getDimension(R.dimen.chart_margin_left)/density;
        rightDpToPx = context.getResources().getDimension(R.dimen.chart_margin_right)/density;
        topDpToPx = context.getResources().getDimension(R.dimen.chart_margin_top)/density;
        bottomDpToPx = context.getResources().getDimension(R.dimen.chart_margin_bottom)/density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        super.setBackgroundColor(Color.WHITE);

        Paint axisLinePaint = new Paint();
        axisLinePaint.setColor(Color.GRAY);
        axisLinePaint.setStrokeWidth(4);

        Paint pointPaint = new Paint();
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(8);

        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(6);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(35);

        AbsAxis xAxis = new XAxis(width, this.height, min(x), max(x));
        xAxis.drawAxisLine(canvas, axisLinePaint);
        xAxis.drawDegree(canvas, pointPaint, textPaint);
        AbsAxis yAxis = new YAxis(this.width, height, min(y), max(y));
        yAxis.drawAxisLine(canvas, axisLinePaint);
        yAxis.drawDegree(canvas, pointPaint, textPaint);

        //TODO only one point
        for (int i = 0; i<x.length-1; i++){
            canvas.drawLine(xAxis.getPixelFrom(x[i]), yAxis.getPixelFrom(y[i]),
                    xAxis.getPixelFrom(x[i+1]),yAxis.getPixelFrom(y[i+1]),pointPaint);
        }
        canvas.save();
    }

    public void setSize(int width, int height) {
        this.height = height- topDpToPx - bottomDpToPx;
        this.width = width - leftDpTpPx - rightDpToPx;
    }

    public void setPoints(float[] x, float[] y) {
        this.x = x;
        this.y = y;
    }

    private float max(float[] arr) {
        float res = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > res) {
                res = arr[i];
            }
        }
        return res;
    }
    private float min(float[] arr) {
        float res = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < res) {
                res = arr[i];
            }
        }
        return res;
    }

}
