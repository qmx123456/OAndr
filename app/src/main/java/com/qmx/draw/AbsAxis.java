package com.qmx.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class AbsAxis {
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

    public void drawDegree(Canvas canvas, Paint pointPaint, Paint textPaint) {
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
    public abstract float getPixelFrom(float degree);
    abstract float[] buildCoordinate(float position);
    public abstract void drawAxisLine(Canvas canvas, Paint paint);
}
