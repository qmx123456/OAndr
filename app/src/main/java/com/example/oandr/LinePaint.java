package com.example.oandr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LinePaint extends View {

    private float width;
    private float height;
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
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        Log.d("oil-line", String.valueOf(height));
        Log.d("oil-line", String.valueOf(width));
        canvas.drawLine(0,height,width,height,paint);
        canvas.save();
    }

    public void setSize(int width, int height) {
        this.height = height- topDpToPx - bottomDpToPx;
        this.width = width - leftDpTpPx - rightDpToPx;
    }
}
