package com.example.yooho.zerostart.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class DiyRulerView extends View {
    private int rulerHeight;                // 尺子高度
    private int rulerToResultGap;           // 尺子到结果的距离
    private int mHeight;
    private int mWidth;
    private RectF bgRect;
    private Paint mBgPaint;
    private int bgColor = Color.parseColor("#F2371E");
    private Paint mCurrentPaint;
    private int curentColor = Color.parseColor("#F5CEBB");
    private int mCurrentNum = 22;
    private Rect currentTextRect;
    private int topGap;
    private RectF bigScaleRect;
    private RectF smallScaleRect;
    private int scaleTopMargin;
    private int bigScaleWidth;
    private int smallScaleWidth;
    private int bigScaleHeight;
    private int smallScaleHeight;
    private int scaleGap;
    private int numGap;
    private int minNum;
    private Paint mScaleSmallPaint;
    private Paint mScaleBigPaint;
    private int smallScaleColor = Color.parseColor("#999999");
    private int bigScaleColor = Color.parseColor("#666666");
    private int maxNum;
    private Rect bigScaleTextRect;
    private int scaleTextGap;


    private int currentDown;
    private int moveX;
    private Rect currentRect;
    private int currentRectWidth;
    private int currentRectHeight;
    private int downNum;
    private ValueAnimator valueAnim;


    public DiyRulerView(Context context) {
        this(context, null);
    }

    public DiyRulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DiyRulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        doInit(attrs, defStyleAttr);
    }

    private void doInit(AttributeSet attrs, int defStyleAttr) {
        readAttribute(attrs, defStyleAttr);
        initStaticRect();
        initPaint();
        initAnimation();
        initSetValue();
    }

    private void initAnimation() {
        valueAnim = new ValueAnimator();
    }

    private void initSetValue() {
        currentRect.set(0, 0, currentRectWidth, currentRectHeight);
        bigScaleRect.set(0, 0, bigScaleWidth, bigScaleHeight);
        smallScaleRect.set(0, 0, smallScaleWidth, smallScaleHeight);


//        compensation = currentRectWidth / 2;
    }

    private void readAttribute(AttributeSet attrs, int defStyleAttr) {
        rulerHeight = 200;
        rulerToResultGap = 40;
        topGap = 10;
        scaleTopMargin = 20;
        scaleGap = 55;
        numGap = 1;

        smallScaleWidth = 5;
        smallScaleHeight = 60;
        bigScaleWidth = 10;
        bigScaleHeight = 100;
        currentRectWidth = 15;
        currentRectHeight = 100;
        scaleTextGap = 10;

        minNum = 0;
        maxNum = 100;
    }

    private void initStaticRect() {
        bgRect = new RectF();
        currentTextRect = new Rect();
        currentRect = new Rect();
        smallScaleRect = new RectF();
        bigScaleRect = new RectF();
        bigScaleTextRect = new Rect();
    }

    private void initPaint() {
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(bgColor);
        mBgPaint.setStyle(Paint.Style.FILL);

        mCurrentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCurrentPaint.setColor(curentColor);
        mCurrentPaint.setStyle(Paint.Style.FILL);

        mScaleSmallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleSmallPaint.setColor(smallScaleColor);
        mScaleSmallPaint.setStyle(Paint.Style.FILL);

        mScaleBigPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleBigPaint.setColor(bigScaleColor);
        mScaleBigPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                mHeight = rulerHeight + rulerToResultGap + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                mHeight = heightSize + getPaddingTop() + getPaddingBottom();
                break;
        }

        mWidth = widthSize + getPaddingLeft() + getRight();
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawCurrent(canvas);
        drawScaleAndNum(canvas);
    }

    private void drawBg(Canvas canvas) {
        bgRect.set(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(bgRect, 20, 20, mBgPaint);
    }

    private void drawScaleAndNum(Canvas canvas) {
        int nowDrawX = mWidth / 2 + moveX % scaleGap;
        int nowNum = mCurrentNum;

        canvas.save();
        int scaleTop = getPaddingTop() + topGap + currentTextRect.height() + scaleTopMargin;

        canvas.translate(nowDrawX, scaleTop);
        drawScale(canvas, 0, nowNum);
        while (nowDrawX >= 0 && nowNum >= minNum) {
            nowDrawX = nowDrawX - scaleGap;
            nowNum = nowNum - numGap;
            drawScale(canvas, -scaleGap, nowNum);
        }
        canvas.restore();

        canvas.save();
        nowDrawX = mWidth / 2 + moveX % scaleGap;
        nowNum = mCurrentNum;
        canvas.translate(nowDrawX, scaleTop);
        while (nowDrawX <= mWidth && nowNum <= maxNum) {
            nowDrawX = nowDrawX + scaleGap;
            nowNum = nowNum + numGap;
            drawScale(canvas, scaleGap, nowNum);
        }

        canvas.restore();
    }

    private void drawScale(Canvas canvas, int nowDrawX, int nowNum) {
        if (nowNum < minNum || nowNum > maxNum) return;
        if (nowNum % 10 == 0) {
            String nowStr = nowNum + "";
            canvas.translate(nowDrawX - bigScaleWidth / 2, 0);
            canvas.drawRoundRect(bigScaleRect, bigScaleWidth, bigScaleWidth, mScaleBigPaint);

            mScaleBigPaint.setTextSize(40);
            mScaleBigPaint.getTextBounds(nowStr, 0, nowStr.length(), bigScaleTextRect);
            canvas.drawText(nowStr, -bigScaleTextRect.width() / 2, bigScaleRect.height() + bigScaleTextRect.height() + scaleTextGap, mScaleBigPaint);
        } else {
            canvas.translate(nowDrawX - smallScaleWidth / 2, 0);
            canvas.drawRoundRect(smallScaleRect, smallScaleWidth, smallScaleWidth, mScaleSmallPaint);
        }
    }


    private void drawCurrent(Canvas canvas) {
        canvas.save();
        String currentStr = mCurrentNum + "";
        mCurrentPaint.setTextSize(50);
        mCurrentPaint.getTextBounds(currentStr, 0, currentStr.length(), currentTextRect);

        canvas.translate(mWidth / 2, getPaddingTop() + topGap);
        canvas.drawText(mCurrentNum + "", -currentTextRect.width() / 2, currentTextRect.height(), mCurrentPaint);
        canvas.drawText("kg", currentTextRect.width() / 2 + 10, currentTextRect.height(), mCurrentPaint);

        canvas.translate(-currentRectWidth / 2, currentTextRect.height() + scaleTopMargin);

        canvas.drawRect(currentRect, mCurrentPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            currentDown = (int) event.getX();
            downNum = mCurrentNum;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            moveX = (int) (event.getX() - currentDown);
            mCurrentNum = downNum - moveX / scaleGap;
            checkBorder();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            int firstX;
            int secondX;
            int add;

            int moveGap = moveX % scaleGap;
            if (moveGap < 0) {
                if (-moveGap < scaleGap / 2) {  //  3.25 > 3
                    firstX = moveGap;
                    secondX = 0;
                    add = 0;
                } else {                        //  3.75 > 4
                    firstX = scaleGap + moveGap;
                    secondX = 0;
                    add = 1;
                }
            } else {
                if (moveGap < scaleGap / 2) {   //  -3.25 > -3
                    firstX = moveGap;
                    secondX = 0;
                    add = 0;
                } else {                        // -3.5 > -4
                    firstX = moveGap - scaleGap;
                    secondX = 0;
                    add = -1;
                }
            }
            mCurrentNum = downNum - moveX / scaleGap + add;
            checkBorder();
            valueAnim = ValueAnimator.ofInt(firstX, secondX);

            valueAnim.setDuration(150);
            valueAnim.setInterpolator(new DecelerateInterpolator());
            valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    moveX = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });

            valueAnim.start();
            return true;
        }
        invalidate();
        return true;
    }

    private void checkBorder() {
        if (mCurrentNum <= minNum) {
            mCurrentNum = minNum;
            moveX = (downNum - mCurrentNum) * scaleGap;
        }
        if (mCurrentNum >= maxNum) {
            mCurrentNum = maxNum;
            moveX = (downNum - mCurrentNum) * scaleGap;
        }
    }
}
