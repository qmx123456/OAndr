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

        drawXDegree(canvas, pointPaint, textPaint, xMax, xMin);
        drawYDegree(canvas,pointPaint,textPaint, yMax, yMin);
        for (int i = 0; i<x.length-1; i++){
//            canvas.drawLine(getXPixel(x[i]), getYPixel(y[i]),x[i+1],y[i+1],pointPaint);
        }

        canvas.save();
    }

    private void drawXDegree(Canvas canvas, Paint pointPaint, Paint textPaint, float max, float min) {
        float diff = (max - min)*extraXY;
        float maxDraw = max + diff;
        float minDraw = min - diff;

        float countMax = width / 150;
        //TODO 优化间隔的取值
        float interval = (maxDraw - minDraw) / countMax;
        float t = interval*((int)Math.ceil(minDraw/interval)+1)-minDraw;
        while (t < maxDraw){
            float pixel = getXPixel(maxDraw, minDraw, t);
            canvas.drawPoint(pixel,height,pointPaint);
            //TODO 优化轴坐标显示位置
            //TODO 优化坐标的数值
            canvas.drawText("("+String.format("%.1f", t)+",y)", pixel, height, textPaint);
            t += interval;
        }
    }

    private float getXPixel(float maxDraw, float minDraw, float t) {
        return (t-minDraw) * width / (maxDraw - minDraw);
    }

    private void drawYDegree(Canvas canvas, Paint pointPaint, Paint textPaint, float max, float min) {
        float diff = (max - min)*extraXY;
        float maxDraw = max + diff;
        float minDraw = min - diff;

        float countMax = height / 150;
        //TODO 优化间隔的取值
        float interval = (maxDraw - minDraw) / countMax;
        float t = interval*((int)Math.ceil(minDraw/interval)+1)-minDraw;
        while (t < maxDraw){
            float pixel = getYPixel(maxDraw, minDraw, t);
            canvas.drawPoint(0,pixel,pointPaint);
            //TODO 优化轴坐标显示位置
            //TODO 优化坐标的数值
            canvas.drawText("(x,"+String.format("%.1f", t)+")", 0, pixel, textPaint);
            t += interval;
        }
    }

    private float getYPixel(float maxDraw, float minDraw, float t) {
        return height - (t-minDraw) * height / (maxDraw - minDraw);
    }

    public void setSize(int width, int height) {
        this.height = height- topDpToPx - bottomDpToPx;
        this.width = width - leftDpTpPx - rightDpToPx;
    }
}
