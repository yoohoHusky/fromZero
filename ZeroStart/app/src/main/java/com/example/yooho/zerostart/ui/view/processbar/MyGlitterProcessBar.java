package com.example.yooho.zerostart.ui.view.processbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.yooho.zerostart.R;

/**
 * Created by haoou on 2018/8/13.
 */

public class MyGlitterProcessBar extends View {
    private Paint borderPaint;
    private int mWidth;
    private int mHeight;
    private Paint glitterPaint;
    private Bitmap glitterBitmap;
    private Canvas processCanvas;
    private Paint processPaint;
    private Bitmap processBitmap;
    private Paint textPaint;

    private String str = "我是变色字体，我要变色了";

    public MyGlitterProcessBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glitterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        processPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        glitterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.flicker);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        mWidth = measureWidth;
        if (heightModel == MeasureSpec.AT_MOST) {
            mHeight = 105;
        }

        setMeasuredDimension(mWidth, mHeight);
        processBitmap = Bitmap.createBitmap(mWidth, mHeight * 2 / 3, Bitmap.Config.ARGB_8888);
        processCanvas = new Canvas(processBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBorder(canvas);

        drawGlitterBar(canvas);
        drawProcess(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(48);

        Rect bound = new Rect();
        textPaint.getTextBounds(str, 0, str.length(), bound);
        canvas.drawText(str, mWidth/5, (getMeasuredHeight() + bound.height())/2, textPaint);


        textPaint.setColor(Color.BLUE);
        canvas.save();
        canvas.clipRect(mWidth/5, 0, mWidth * 2/5 + 10, getMeasuredHeight());
        canvas.drawText(str, mWidth/5, (getMeasuredHeight() + bound.height())/2, textPaint);
        canvas.restore();
    }

    @SuppressLint("WrongConstant")
    private void drawProcess(Canvas canvas) {
//        processCanvas.drawColor(Color.RED);
        processCanvas.save();
        processCanvas.clipRect(0, 0, mWidth*3/4, mHeight*2/3);
        processCanvas.drawColor(Color.YELLOW);
        processCanvas.restore();

//        processPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        processCanvas.drawBitmap(glitterBitmap, mWidth*3/4 -120, 0, processPaint);
//        processPaint.setXfermode(null);

        canvas.drawBitmap(processBitmap, 0, 0, processPaint);
    }

    private void drawGlitterBar(Canvas canvas) {
        glitterPaint.setStyle(Paint.Style.FILL);



    }

    private void drawBorder(Canvas canvas) {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);
        borderPaint.setColor(Color.parseColor("#40c4ff"));

        canvas.drawRect(0, 0, mWidth, mHeight, borderPaint);
    }
}
