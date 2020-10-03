package com.qmx.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

public class YAxis extends AbsAxis {
    private final float height;
    private final float width;
    public YAxis(float width, float height, float min, float max){
        super(height, min, max);
        this.height = height;
        this.width = width;
    }

    @Override
    public float getPixelFrom(float degree) {
        return height - (degree -minDraw) * height / (maxDraw - minDraw);
    }
    @Override
    float[] buildCoordinate(float position) {
        return new float[]{0, position};
    }

    @Override
    public void drawAxisLine(Canvas canvas, Paint paint) {
        canvas.drawLine(0,height,0,0, paint);
    }
}
