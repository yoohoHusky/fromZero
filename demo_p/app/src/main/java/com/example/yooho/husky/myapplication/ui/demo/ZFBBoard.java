package com.example.yooho.husky.myapplication.ui.demo;

import android.animation.IntEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.yooho.husky.myapplication.R;


/**
 * Created by haoou on 2018/8/7.
 */

public class ZFBBoard extends View {
    int globalScore = 0;

    Paint mBgPaint;
    Paint mEdgePaint;
    Paint mEdgeTextPaint;
    Paint mCenterTextPaint;
    private int measureWidth;
    private int measureHeight;
    private int borderWidth;
    private int borderLeft;
    private int borderTop;


    int totalCount = 70;
    int preAngle = 3;
    int totalAngle = preAngle * totalCount;


    int bigEdgeScaleWidth = 6;
    int bigEdgeScaleLength = 30;

    int smallEdgeScaleWidth = 4;
    int smallEdgeScaleLength = 15;

    int edgeTextMarginScale = 40;
    int edgeTextSize = 50;

    int centerTextSize = 70;



    public ZFBBoard(Context context) {
        this(context, null);
    }

    public ZFBBoard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZFBBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBgPaint = new Paint();
        mEdgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEdgeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ZFBBoard, defStyleAttr, 0);
        String string = typedArray.getString(R.styleable.ZFBBoard_mText);
        int color = typedArray.getColor(R.styleable.ZFBBoard_mColor, 0xff00ff);
        Log.e("SS", string);

        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(1000);
        animator.setObjectValues(new int[2], new int[2]);
        animator.setEvaluator(new TypeEvaluator<int[]>() {

            @Override
            public int[] evaluate(float fraction, int[] startValue, int[] endValue) {
                return new int[0];
            }
        });
        animator.setEvaluator(new IntEvaluator());
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Log.e("SS", "sddsdsd");
        drawBackground(canvas, globalScore);
        drawEdgeArc(canvas);
        drawEdgeText(canvas, globalScore);

    }

    private void drawEdgeText(Canvas canvas, int score) {
        canvas.save();
        Rect rect = new Rect();
        String centerText;
        String centerEvaluation;

        canvas.translate(measureWidth/2, borderTop + borderWidth/2);
        mCenterTextPaint.setColor(Color.WHITE);
        mCenterTextPaint.setTextSize(55);
        mCenterTextPaint.setTextAlign(Paint.Align.CENTER);

        centerText = "您当前的分值为：" + score;
        canvas.drawText(centerText, 0, 30, mCenterTextPaint);

        mCenterTextPaint.setTextSize(centerTextSize);
        mCenterTextPaint.setColor(Color.YELLOW);
        if (score < 20) {
            centerEvaluation = "一般";
        } else if (score < 40) {
            centerEvaluation = "良好";
        } else if (score < 60) {
            centerEvaluation = "优秀";
        } else {
            centerEvaluation = "极好";
        }
        canvas.drawText(centerEvaluation, 0, -100, mCenterTextPaint);
        canvas.restore();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawEdgeArc(Canvas canvas) {

        mEdgePaint.setStyle(Paint.Style.STROKE);
        mEdgePaint.setColor(Color.WHITE);
        mEdgePaint.setStrokeWidth(6);

        mEdgeTextPaint.setColor(Color.GREEN);
        mEdgeTextPaint.setTextSize(edgeTextSize);
//        mEdgeTextPaint.setTextAlign(Paint.Align.CENTER);
        Rect bound = new Rect();
        String edgeText;

        // 1/画边缘弧线
        RectF rectF = new RectF(borderLeft, borderTop, borderLeft+borderWidth, borderTop+borderWidth);
        canvas.drawArc(rectF, (180 - totalAngle/2) + 90, totalAngle, false, mEdgePaint);

        // 2/画边缘刻度线
        canvas.save();
        canvas.translate(measureWidth / 2, borderTop + borderWidth / 2);
        canvas.rotate(-totalAngle/2);

        // 2.1 画0、10、20整十位的刻度线
        for (int m = 0; m < totalCount/10; m++) {
            mEdgePaint.setStrokeWidth(bigEdgeScaleWidth);
            canvas.drawLine(0, -borderWidth/2, 0, -borderWidth/2 + bigEdgeScaleLength, mEdgePaint);


            // 2.4 画刻度文字
            edgeText = m*10 + "";
            mEdgeTextPaint.getTextBounds(edgeText, 0, edgeText.length(), bound);
            canvas.drawText(edgeText, -bound.width()/2, -borderWidth/2 + bigEdgeScaleLength + edgeTextMarginScale, mEdgeTextPaint);
//            canvas.drawText(edgeText, 0, -borderWidth/2 + bigEdgeScaleLength + edgeTextMarginScale, mEdgeTextPaint);

            Paint.FontMetricsInt fontMetricsInt = mEdgeTextPaint.getFontMetricsInt();
            int top = fontMetricsInt.leading;


            // 2.2 画x1 ~ x9 个位数的刻度线
            for (int n = 1; n < 10; n++) {
                canvas.rotate(preAngle);
                mEdgePaint.setStrokeWidth(smallEdgeScaleWidth);
                canvas.drawLine(0, -borderWidth/2, 0, -borderWidth/2 + smallEdgeScaleLength, mEdgePaint);
            }
            canvas.rotate(preAngle);
        }
        // 3/画结尾内容
        mEdgePaint.setStrokeWidth(bigEdgeScaleWidth);
        canvas.drawLine(0, -borderWidth/2, 0, -borderWidth/2 + bigEdgeScaleLength, mEdgePaint);

        edgeText = totalCount + "";
        mEdgeTextPaint.getTextBounds(edgeText, 0, edgeText.length(), bound);
        canvas.drawText(edgeText, -bound.width()/2, -borderWidth/2 + bigEdgeScaleLength + edgeTextMarginScale, mEdgeTextPaint);

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MeasureSpec.getMode(widthMeasureSpec);
        measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        Log.e("SS", "measureWidth: " + measureWidth);
        borderWidth = measureWidth * 4 / 5;
        borderLeft = (measureWidth - borderWidth) / 2;

        MeasureSpec.getMode(heightMeasureSpec);
        measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        borderTop = measureHeight / 8;

        setMeasuredDimension(measureWidth, measureHeight);
    }

    private void drawBackground(Canvas canvas, int score) {
        canvas.drawColor(Color.RED);

    }

    public void updateScore(int score) {
        globalScore = score;
        invalidate();
    }
}
