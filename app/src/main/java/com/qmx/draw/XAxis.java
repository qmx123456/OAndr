package com.qmx.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

public class XAxis extends AbsAxis {
//TODO XAxis与YAxis的优化
    private float width;
    private float height;
    public XAxis(float width, float height, float min, float max) {
        super(width, min, max);
        this.width = width;
        this.height = height;
    }
    @Override
    public float getPixelFrom(float degree) {
        return (degree -minDraw) * width / (maxDraw - minDraw);
    }
    @Override
    float[] buildCoordinate(float position) {
        return new float[]{position, this.height};
    }
    @Override
    public void drawAxisLine(Canvas canvas, Paint paint) {
        canvas.drawLine(0,height,width,height, paint);
    }
}
