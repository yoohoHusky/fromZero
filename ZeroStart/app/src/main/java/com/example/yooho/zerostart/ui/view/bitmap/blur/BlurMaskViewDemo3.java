package com.example.yooho.zerostart.ui.view.bitmap.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class BlurMaskViewDemo3 extends View {
    Paint mPaint;
    Paint paint2;
    Bitmap bitmap;
    private static int increaseHeight = 800;


    public BlurMaskViewDemo3(Context context) {
        this(context, null);
    }

    public BlurMaskViewDemo3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BlurMaskViewDemo3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTools(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(width, 1600);
    }

    private void initTools(Context context) {
        mPaint = new Paint();
        paint2 = new Paint();
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = 70;
        float w = 20;

        canvas.drawColor(Color.RED);
        canvas.translate(radius + w, radius + w);
        int[] indicatorColor = {0xffffffff,0x00ffffff,0x99ffffff,0xffffffff};
        Shader shader = new SweepGradient(0,0, indicatorColor, null);
        LinearGradient gradient = new LinearGradient(0,0, 100, 100, indicatorColor, null, LinearGradient.TileMode.REPEAT);

        paint2.setShader(gradient);
        RectF rectF = new RectF(-radius-w, -radius-w, radius+w, radius+w);
//        canvas.drawArc(rectF, 0, 240, true, paint2);
        canvas.drawRect(rectF, paint2);


    }

}
