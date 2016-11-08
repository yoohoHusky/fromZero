package com.example.yooho.zerostart.ui.view.line;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yooho on 2016/10/31.
 *
 * 1. 使画笔画的线是虚线
 *  paint.setPathEffect(new DashPathEffect(new float[]{1.0f, 2.0f, 4.0f, 8.0f}, 30.0f));
 *
 * 2. 为view设置属性,并通过动画效果展现
 *  ObjectAnimator animator = ObjectAnimator.ofFloat(DynamicalLine.this, "alpha", 0.0f, 1.0f);  //设置透明度
 *  ObjectAnimator animator = ObjectAnimator.ofFloat(DynamicalLine.this, "phase", 0.0f, 1.0f);  //设置进度
 *  animator.setDuration(3000);
 *  animator.start();
 */



public class DynamicalLine extends View {
    Path path;
    Paint paint;
    float length;

    public DynamicalLine(Context context)
    {
        super(context);
    }

    public DynamicalLine(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DynamicalLine(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void init()
    {
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        path.moveTo(50, 50);
        path.lineTo(50, 500);
        path.lineTo(200, 500);
        path.lineTo(200, 300);
        path.lineTo(350, 300);
        path.quadTo(360,100,400,310);

        // Measure the path
        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        float[] intervals = new float[]{length, length};

        paint.setPathEffect(new DashPathEffect(new float[]{1.0f, 2.0f, 4.0f, 8.0f}, 30.0f));

        ObjectAnimator animator = ObjectAnimator.ofFloat(DynamicalLine.this, "phase", 0.0f, 1.0f);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(DynamicalLine.this, "alpha", 0.0f, 1.0f);
        animator.setDuration(3000);
        animator.start();


    }

    //is called by animtor object
    public void setPhase(float phase)
    {
        Log.d("pathview", "setPhase called with:" + String.valueOf(phase));
        paint.setPathEffect(createPathEffect(length, phase, 0.0f));
        invalidate();//will calll onDraw
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset)
    {
        return new DashPathEffect(new float[] { pathLength, pathLength },
                pathLength - phase * pathLength);
//        return new DashPathEffect(new float[] { phase*pathLength, pathLength },
//               0);
    }

    @Override
    public void onDraw(Canvas c)
    {
        super.onDraw(c);
        c.drawPath(path, paint);
    }
}
