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

        AbsAxis xAxis = new XAxis(width, LinePaint.this.height, min(x), max(x));
        xAxis.drawAxisLine(canvas, axisLinePaint);
        xAxis.drawDegree(canvas, pointPaint, textPaint);
        AbsAxis yAxis = new YAxis(LinePaint.this.width, height, min(y), max(y));
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
    //TODO XAxis与YAxis的优化
    private class XAxis extends AbsAxis {
        private float width;
        private float height;
        public XAxis(float width, float height, float min, float max) {
            super(width, min, max);
            this.width = width;
            this.height = height;
        }
        @Override
        float getPixelFrom(float degree) {
            return (degree -minDraw) * width / (maxDraw - minDraw);
        }
        @Override
        float[] buildCoordinate(float position) {
            return new float[]{position, this.height};
        }
        @Override
        void drawAxisLine(Canvas canvas, Paint paint) {
            canvas.drawLine(0,height,width,height, paint);
        }
    }

    private class YAxis extends AbsAxis {
        private final float height;
        private final float width;
        public YAxis(float width, float height, float min, float max){
            super(height, min, max);
            this.height = height;
            this.width = width;
        }

        @Override
        float getPixelFrom(float degree) {
            return height - (degree -minDraw) * height / (maxDraw - minDraw);
        }
        @Override
        float[] buildCoordinate(float position) {
            return new float[]{0, position};
        }

        @Override
        void drawAxisLine(Canvas canvas, Paint paint) {
            canvas.drawLine(0,height,0,0, paint);
        }
    }

    private abstract class AbsAxis {
        private final int intervalPixel = 150;
        private final float extra = 0.1f;
        protected final float maxDraw;
        protected final float minDraw;
        protected final float interval;

        public AbsAxis(float length, float min, float max) {
            float diff = (max - min)* extra;
            maxDraw = max + diff;
            minDraw = min - diff;
            //TODO 优化间隔的取值
            interval = (maxDraw - minDraw) / (length / intervalPixel);
        }

        protected void drawDegree(Canvas canvas, Paint pointPaint, Paint textPaint) {
            //TODO 优化坐标的数值
            float degree = interval*((int)Math.floor(minDraw/interval)+1);
            while (degree < maxDraw){
                //TODO 优化轴坐标显示位置
                float position = getPixelFrom(degree);
                float[] pos = buildCoordinate(position);
                canvas.drawPoint(pos[0],pos[1], pointPaint);
                canvas.drawText(String.format("%.1f", degree), pos[0],pos[1], textPaint);
                degree += interval;
            }
        }
        abstract float getPixelFrom(float degree);
        abstract float[] buildCoordinate(float position);
        abstract void drawAxisLine(Canvas canvas, Paint paint);
    }
}
