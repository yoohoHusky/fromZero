package com.example.yooho.zerostart.ui.view.bitmap.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.yooho.zerostart.R;

public class BlurMaskViewDemo2 extends View {
    Paint mPaint;
    Paint mPaint2;
    Bitmap bitmap;
    private static int increaseHeight = 800;


    public BlurMaskViewDemo2(Context context) {
        this(context, null);
    }

    public BlurMaskViewDemo2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BlurMaskViewDemo2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTools(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(width, 1800);
    }

    private void initTools(Context context) {
        mPaint = new Paint();
        mPaint2 = new Paint();
        mPaint2.setTextSize(24);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmap == null) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ez);

        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));
        canvas.drawBitmap(bitmap, 50, (bitmap.getHeight() + 100) * 0 + 100 , mPaint);
        canvas.drawText("OUTER", 50 + bitmap.getWidth() + 50, (bitmap.getHeight() + 100) * 0 + 100, mPaint2);

        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER));
        canvas.drawBitmap(bitmap, 50, (bitmap.getHeight() + 100) * 1+ 100, mPaint);
        canvas.drawText("INNER", 50 + bitmap.getWidth() + 50, (bitmap.getHeight() + 100) * 1 + 100, mPaint2);

        Bitmap grayBitmap = bitmap.extractAlpha();
        mPaint.setColor(Color.GRAY);
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
        canvas.drawBitmap(grayBitmap, 50,  (bitmap.getHeight() + 100) * 2+ 100, mPaint);
        canvas.drawBitmap(bitmap, 50, (bitmap.getHeight() + 100) * 2+ 100, null);
        canvas.drawText("Gray SOLID + none", 50 + bitmap.getWidth() + 50, (bitmap.getHeight() + 100) * 2 + 100, mPaint2);


    }
}
