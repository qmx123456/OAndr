package com.example.oandr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

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

        canvas.drawLine(0,height,width,height,axisLinePaint);
        canvas.drawLine(0,height,0,0,axisLinePaint);

        AbsAxis xAxis = new XAxis(max(x), min(x),width);
        xAxis.drawDegree(canvas, pointPaint, textPaint);
        AbsAxis yAxis = new YAxis(max(y), min(y), height);
        yAxis.drawDegree(canvas, pointPaint, textPaint);

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
        float min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
    //TODO XAxis 与YAxis的优化
    private class XAxis extends AbsAxis {
        public XAxis(float max, float min, float width) {
            super(max, min, width);
        }
        @Override
        protected float getPixelFrom(float degree) {
            return (degree -minDraw) * length / (maxDraw - minDraw);
        }

        protected void draw(Canvas canvas, Paint pointPaint, Paint textPaint, float degree, float position) {
            canvas.drawPoint(position,height,pointPaint);
            canvas.drawText(String.format("%.1f", degree), position, height, textPaint);
        }
    }

    private class YAxis extends AbsAxis {
        public YAxis(float max, float min, float height){
            super(max, min, height);
        }

        @Override
        protected float getPixelFrom(float degree) {
            return length - (degree -minDraw) * length / (maxDraw - minDraw);
        }
        protected void draw(Canvas canvas, Paint pointPaint, Paint textPaint, float degree, float position) {
            canvas.drawPoint(0,position,pointPaint);
            canvas.drawText(String.format("%.1f", degree), 0, position, textPaint);
        }
    }

    private abstract class AbsAxis {
        private final int intervalPixel = 150;;
        private final float extra = 0.1f;
        protected final float maxDraw;
        protected final float minDraw;
        protected final float interval;
        protected final float length;

        public AbsAxis(float max, float min, float length) {
            float diff = (max - min)* extra;
            maxDraw = max + diff;
            minDraw = min - diff;
            //TODO 优化间隔的取值
            this.length = length;
            interval = (maxDraw - minDraw) / (this.length / intervalPixel);
        }

        protected void drawDegree(Canvas canvas, Paint pointPaint, Paint textPaint) {
            //TODO 优化坐标的数值
            float degree = interval*((int)Math.floor(minDraw/interval)+1);
            while (degree < maxDraw){
                //TODO 优化轴坐标显示位置
                float position = getPixelFrom(degree);
                draw(canvas, pointPaint, textPaint, degree, position);
                degree += interval;
            }
        }
        abstract void draw(Canvas canvas, Paint pointPaint, Paint textPaint, float degree, float position);
        abstract float getPixelFrom(float degree);
    }
}
