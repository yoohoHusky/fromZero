package com.example.yooho.zerostart.ui.view.bitmap.splite;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CrashBall extends View {

    private float mMaxX = 400;//X最大值
    private float mMinX = -400;//X最小值
    private float mMaxY = 300;//Y最大值
    private float mMinY = -100;//Y最小值
    private float defaultR = 20;//默认小球半径
    private int defaultColor = Color.BLUE;//默认小球颜色
    private float defaultVX = 6;//默认小球x方向速度
    private float defaultVY = 0;//默认小球y方向速度

    private float defaultAX = 0;//默认小球y方向速度
    private float defaultAY = 1;//默认小球y方向速度
    private float defaultF = 0.95f;


    private Paint mPaint;
    private Paint mBorderPaint;
    private List<Ball> ballList;
    private ValueAnimator animat;


    public CrashBall(Context context) {
        super(context);
    }
    public CrashBall(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(2);
        mBorderPaint.setColor(Color.BLACK);


        Ball ball = new Ball();
        ball.color = defaultColor;
        ball.vX = defaultVX;
        ball.vY = defaultVY;
        ball.aY = defaultAY;
        ball.r = defaultR;

        ballList = new ArrayList<>();
        ballList.add(ball);

        animat = ValueAnimator.ofInt(0, 1);
        animat.setRepeatCount(-1);
        animat.setDuration(2000);
        animat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBallList(ballList);
                invalidate();
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 900);
    }

    private void updateBallList(List<Ball> ballList) {

        for (int i = 0; i < ballList.size(); i++) {

            Ball ball = ballList.get(i);
            ball.vY += ball.aY;
            ball.x += ball.vX;
            ball.y += ball.vY;
            if (ball.r < 1) {
                ballList.remove(ball);
            }

            if (ball.x < mMinX) {
                Ball newBall = ball.clone();
                newBall.r = newBall.r / 2;
                newBall.vX = -newBall.vX;
                newBall.vY = -newBall.vY;
                ballList.add(newBall);

                ball.r = ball.r / 2;
                ball.vX = -ball.vX * defaultF;
                ball.x = mMinX;
            }
            if (ball.x > mMaxX) {
                ball.x = mMaxX;
                ball.vX = - ball.vX;
            }
            if (ball.y < mMinY) {
                ball.y = mMinY;
                ball.vY = - ball.vY;
            }
            if (ball.y > mMaxY) {
                ball.y = mMaxY;
                ball.vY = - ball.vY;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        animat.start();
        return super.onTouchEvent(event);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(500, 500);
        canvas.drawRect(mMinX - defaultR, mMinY- defaultR, mMaxX + defaultR, mMaxY+ defaultR, mBorderPaint);
        for (Ball ball : ballList) {
            mPaint.setColor(ball.color);
            canvas.drawCircle(ball.x, ball.y, ball.r * 2, mPaint);
        }
        canvas.restore();
    }
}




















