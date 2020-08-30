package com.example.oandr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class LinePaint extends View {

    private final int yMax = 5;
    private final int yMin = 1;
    private float width;
    private float height;
    private float[] x = new float[]{1,2,3,4,5};
    private float[] y = new float[]{1,2,3,4,5};
    private float xMin = 1;
    private float xMax = 5;
    private float extraXY = 0.1f;
    private final float leftDpTpPx;
    private final float rightDpToPx;
    private final float topDpToPx;
    private final float bottomDpToPx;

    public LinePaint(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setBackgroundColor(Color.WHITE);

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

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        canvas.drawLine(0,height,width,height,paint);
        canvas.drawLine(0,height,0,0,paint);

        Paint pointPaint = new Paint();
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(10);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(10);
        textPaint.setTextSize(35);


        XAxis xAxis = new XAxis(max(x), min(x));
        xAxis.drawDegree(canvas, pointPaint, textPaint);
        YAxis yAxis = new YAxis(max(y), min(y));
        yAxis.drawDegree(canvas, pointPaint, textPaint);

        for (int i = 0; i<x.length-1; i++){
            canvas.drawLine(xAxis.getPixel(x[i]), yAxis.getPixel(y[i]),
                    xAxis.getPixel(x[i+1]),yAxis.getPixel(y[i+1]),pointPaint);
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
    private class XAxis {
        private final float maxDraw;
        private final float minDraw;
        private final float interval;

        public XAxis(float max, float min) {
            float diff = (max - min)*extraXY;
            maxDraw = max + diff;
            minDraw = min - diff;

            float countMax = width / 150;
            //TODO 优化间隔的取值
            interval = (maxDraw - minDraw) / countMax;
        }

        private float getPixel(float t) {
            return (t-minDraw) * width / (maxDraw - minDraw);
        }

        private void drawDegree(Canvas canvas, Paint pointPaint, Paint textPaint) {
            float t = interval*((int)Math.ceil(minDraw/interval)+1)-minDraw;
            while (t < maxDraw){
                float pixel = getPixel(t);
                canvas.drawPoint(pixel,height,pointPaint);
                //TODO 优化轴坐标显示位置
                //TODO 优化坐标的数值
                canvas.drawText(String.format("%.1f", t), pixel, height, textPaint);
                t += interval;
            }
        }
    }

    private class YAxis {
        private final float maxDraw;
        private final float minDraw;
        private final float interval;

        public YAxis(float max, float min) {
            float diff = (max - min)*extraXY;
            maxDraw = max + diff;
            minDraw = min - diff;

            float countMax = height / 150;
            //TODO 优化间隔的取值
            interval = (maxDraw - minDraw) / countMax;
        }
        private void drawDegree(Canvas canvas, Paint pointPaint, Paint textPaint) {
            float t = interval*((int)Math.ceil(minDraw/interval)+1)-minDraw;
            while (t < maxDraw){
                float pixel = getPixel(t);
                canvas.drawPoint(0,pixel,pointPaint);
                //TODO 优化轴坐标显示位置
                //TODO 优化坐标的数值
                canvas.drawText(String.format("%.1f", t), 100, pixel, textPaint);
                t += interval;
            }
        }
        private float getPixel(float t) {
            return height - (t-minDraw) * height / (maxDraw - minDraw);
        }
    }
}
