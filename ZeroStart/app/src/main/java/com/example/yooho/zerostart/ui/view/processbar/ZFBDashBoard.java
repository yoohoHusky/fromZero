package com.example.yooho.zerostart.ui.view.processbar;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.example.yooho.zerostart.tools.DisplayUtil;

/**
 * Created by yooho on 2016/12/9.
 */
public class ZFBDashBoard extends View {

    Paint paint;
    Paint paint1;
    Paint paint2;
    Paint paint3;
    private int radius;
    int sizeWidth, sizeHeight;

    int groupNum = 5;
    int groupChildNum = 6;

    int startAngle=160, sweepAngle=220;
    int maxNum = groupNum * 100;
    private float mOut2Radius = 20;
    private int mCurrentNum;

    String[] evaluateList = {"较差", "中等", "良好", "优秀", "极好"};

    public ZFBDashBoard(Context context) {
        this(context, null);
    }

    public ZFBDashBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZFBDashBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributeValue(attrs);
    }

    private void getAttributeValue(AttributeSet attrs) {
//        getResources().obtainAttributes()
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffffff);

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode1 = MeasureSpec.getMode(heightMeasureSpec);
        int size1 = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            sizeWidth = size;
        } else {
            sizeWidth = DisplayUtil.px2dip(getContext(), 300);
        }

        if (mode1 == MeasureSpec.EXACTLY) {
            sizeHeight = size1;
        } else {
            sizeHeight = DisplayUtil.px2dip(getContext(), 400);
        }
        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    int sweepInWidth = DisplayUtil.px2dip(getContext(), 16);
    int sweepOutWidth = DisplayUtil.px2dip(getContext(), 6);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        radius = getMeasuredWidth()/4;
        canvas.save();
        canvas.translate(sizeWidth/2, sizeHeight/2);
        drawRound(canvas);
        drawScale(canvas);
        drawIndicator(canvas);
        drawCenterText(canvas);


        canvas.restore();

    }

    private void drawCenterText(Canvas canvas) {
        int i = mCurrentNum / 100;
        String centerStr = mCurrentNum + "";

        canvas.save();
        paint3.setStyle(Paint.Style.FILL);
        paint3.setTextSize(DisplayUtil.px2sp(getContext(), 25));
        paint3.setColor(Color.parseColor("#88ff88"));
        canvas.drawText(centerStr, -paint3.measureText(centerStr)/2, 0, paint3);

        String addStr = "信用" + evaluateList[i];
        Rect rect = new Rect();
        paint3.getTextBounds(addStr, 0, addStr.length(), rect);
        canvas.drawText(addStr, -rect.width()/2, rect.height()/2 + 20, paint3);

        String descriptionStr = "m.toutiao.com";
        canvas.drawText(descriptionStr, -paint3.measureText(descriptionStr)/2, 50, paint3);

        canvas.restore();

    }

    int[] indicatorColor = {0xffffffff,0x00ffffff,0x99ffffff,0xffffffff};

    private void drawIndicator(Canvas canvas) {
        canvas.save();
        paint2.setStyle(Paint.Style.STROKE);
        int sweep;
        if (mCurrentNum <= maxNum) {
            sweep = mCurrentNum * sweepAngle / maxNum;
        } else {
            sweep = sweepAngle;
        }
        if (sweep == 0) {
            sweep = 1;
        }
        paint2.setStrokeWidth(sweepOutWidth);

        Shader shader = new SweepGradient(0,0, indicatorColor, null);
        paint2.setShader(shader);
        int w = DisplayUtil.px2dip(getContext(), mOut2Radius);
        RectF rectF = new RectF(-radius-w, -radius-w, radius+w, radius+w);
        canvas.drawArc(rectF, startAngle, sweep, false, paint2);

        float x = (float) ((radius+w)* Math.cos(Math.toRadians(startAngle + sweep)));
        float y = (float) ((radius+w)* Math.sin(Math.toRadians(startAngle + sweep)));
        paint3.setStyle(Paint.Style.FILL);
        paint3.setColor(0xffffffff);
        int i = DisplayUtil.px2dip(getContext(), 6);
        paint3.setMaskFilter(new BlurMaskFilter(i, BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle(x, y, i, paint3);

        canvas.restore();
    }

    private void drawScale(Canvas canvas) {
        int totalCount = groupNum * groupChildNum;
        float stepRotate = sweepAngle*1.0f / totalCount;

        canvas.save();
        canvas.rotate(startAngle + 90);     // 逆时针方向转
        for (int group=0; group<groupNum; group++) {
            for (int child=0; child<groupChildNum; child++) {
                paint.setStrokeWidth(DisplayUtil.px2dip(getContext(), 4));
                paint.setAlpha(0x70);
                if (child == 0) {
                    paint.setAlpha(0xa0);
                    canvas.drawLine(0, -radius-sweepInWidth/2, 0, -radius+sweepInWidth/2+5, paint);
                    drawInText(canvas, group*100 + "", paint1);
                } else if (child == 3) {
                    canvas.drawLine(0, -radius-sweepInWidth/2, 0, -radius+sweepInWidth/2, paint);
                    drawInText(canvas, evaluateList[group], paint1);
                } else {
                    canvas.drawLine(0, -radius-sweepInWidth/2, 0, -radius+sweepInWidth/2, paint);
                }
                canvas.rotate(stepRotate);
            }
        }
        drawLastLine(canvas);
        canvas.restore();
    }

    private void drawLastLine(Canvas canvas) {
        canvas.drawLine(0, -radius-sweepInWidth/2, 0, -radius+sweepInWidth/2+5, paint);
        drawInText(canvas, (groupNum)*100 + "", paint1);
    }

    private void drawInText(Canvas canvas, String s, Paint paint) {
        paint1.setColor(Color.WHITE);
        paint1.setTextSize(15);
        Rect rect = new Rect();
        paint.getTextBounds(s, 0, s.length(), rect);
        int textHeight = rect.bottom - rect.top;
        int textWidth = rect.right - rect.left;
        canvas.drawText(s, (float) (0-textWidth/2.), -radius+sweepInWidth/2 + 5 + + 10 + textHeight/3, paint1);

    }

    private void drawRound(Canvas canvas) {
        canvas.save();
        paint.setAlpha(0x40);
        paint.setStrokeWidth(sweepInWidth);
        RectF rectF = new RectF(-radius, -radius, radius, radius);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);

        paint.setStrokeWidth(sweepOutWidth);
        int w = DisplayUtil.px2dip(getContext(), mOut2Radius);
        RectF rectF1 = new RectF(-radius - w, -radius - w, radius + w, radius + w);
        canvas.drawArc(rectF1, startAngle, sweepAngle, false, paint);
        canvas.restore();
    }


    private int getCurrentNum() {
        return mCurrentNum;
    }

    private void setCurrentNum(int num) {
        mCurrentNum = num;
        invalidate();
    }

    public void setCurrentNumAnim(int num) {
        num = Math.min(num, maxNum-1);
        float duration = Math.abs(mCurrentNum - num) * 1550/maxNum + 500;
        ObjectAnimator numAnim = ObjectAnimator.ofInt(this, "currentNum", mCurrentNum, num);
        numAnim.setDuration((long) Math.min(duration, 2000));
        numAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                int color = calculateColor(value);
                setBackgroundColor(color);
            }
        });
        numAnim.start();
    }

    private int calculateColor(int value) {
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        float fraction = 0;
        int color = 0;
        if (value <= maxNum/2) {
            fraction = value*1.0f/(maxNum/2);
            color = (int) argbEvaluator.evaluate(fraction, 0xFFFF6347, 0xFFFF8C00);
        } else {
            fraction = (value*1.0f-maxNum/2)/(maxNum/2);
            color = (int) argbEvaluator.evaluate(fraction, 0xFFFF8C00, 0xFF00CED1);
        }
        return color;
    }
}









