package com.example.yooho.zerostart.ui.view.weather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.tools.DisplayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yooho on 2016/11/5.
 */
public class OneDay24HourView extends View {

    Paint mTemperaturePaint, mWindyBoxPaint, mTimeTextPaint;
    Paint mLinePaint;
    private int ITEM_SIZE = 24;
    private List<HourItem> mItemInfoList;
    private List<Integer> mWindyList;
    private List<Integer> mTemperatureList;

    private static final int DEFAULT_HEIGHT = 500;
    private static final int MARGIN_LEFT_ITEM = 100;
    private static final int MARGIN_RIGHT_ITEM = 100;
    private static final int ITEM_WIDTH = 140;

    private static final int MAX_TEMPERATURE_VALUE = 45;
    private static final int MIN_TEMPERATURE_VALUE = -35;
    private int MAX_TEMPERATURE_HEIGHT = DEFAULT_HEIGHT - WINDY_TEXT_HEIGHT- MAX_WINDY_HEIGHT - MAX_TIME_TEXT_HEIGHT;
    private int MIN_TEMPERATURE_HEIGHT = 20 + TEMPERATURE_TEXT_HEIGHT;
    private static final int TEMPERATURE_TEXT_HEIGHT = 60;

    private static final int MAX_WINDY = 10;
    private static final int MIN_WINDY = 1;
    private static final int MAX_WINDY_HEIGHT = 120;
    private static final int MIN_WINDY_HEIGHT = 20;
    private static final int WINDY_TEXT_HEIGHT = 30;

    private static final int MAX_TIME_TEXT_HEIGHT = 60;

    private static final int TEMPERATURE_PIC_TOP_MARGIN = 28;
    private static final int TEMPERATURE_PIC_BOTTOM_MARGIN = 8;
    private static final int TEMPERATURE_PIC_WIDTH = 18;


    private static final int WEATHER_RES[] ={R.mipmap.w0, R.mipmap.w1, R.mipmap.w3, -1, -1
            ,R.mipmap.w5, R.mipmap.w7, R.mipmap.w9, -1, -1
            ,-1, R.mipmap.w10, R.mipmap.w15, -1, -1
            ,-1, -1, -1, -1, -1
            ,R.mipmap.w18, -1, -1, R.mipmap.w19};


    private int mHeight, mWidth;
    private int mBottomTextHeight, mWindyBoxMaxHeight;

    private int mRealScrollX;
    private int mMaxOffset;
    private int mScrollIndex;

    public OneDay24HourView(Context context) {
        this(context, null);
    }

    public OneDay24HourView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OneDay24HourView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHeight = 500;
        mWidth = MARGIN_LEFT_ITEM + ITEM_SIZE * ITEM_WIDTH + MARGIN_RIGHT_ITEM;
        mBottomTextHeight = WINDY_TEXT_HEIGHT;

        mWindyList = new ArrayList<>();
        mTemperatureList = new ArrayList<>();
        mItemInfoList = new ArrayList<>();

        createFakeDate();
        initItemData();
        initViewTools();
    }

    private void createFakeDate() {
        for (int i=0; i<ITEM_SIZE; i++) {
            if (i == 0) {
                mWindyList.add(MAX_WINDY);
                mTemperatureList.add(MIN_TEMPERATURE_VALUE);
            } else {
                Random random = new Random();
                mWindyList.add(random.nextInt(6));
                mTemperatureList.add(random.nextInt(MAX_TEMPERATURE_VALUE - MIN_TEMPERATURE_VALUE) + MIN_TEMPERATURE_VALUE);
            }
        }

    }

    private void initItemData() {
        for (int i = 0; i < ITEM_SIZE; i++) {
            String time = getItemTime(i);
            Rect windyRect = getWindyRect(i);
            Point temperaturePoint = getTemperaturePoint((windyRect.left + windyRect.right)/2, i);

            HourItem hourItem = new HourItem();
            hourItem.temperature = mTemperatureList.get(i);
            hourItem.tempPoint = temperaturePoint;
            hourItem.windy = mWindyList.get(i);
            hourItem.windyBoxRect = windyRect;
            hourItem.time = time;
            hourItem.res = WEATHER_RES[i];

            mItemInfoList.add(hourItem);
        }
    }

    private Rect getWindyRect(int i) {
        int windyLeft = MARGIN_LEFT_ITEM + i * ITEM_WIDTH;
        int windyRight = windyLeft + ITEM_WIDTH - 1;
        int windyTop = (int) (mHeight
                - mBottomTextHeight
                - MAX_WINDY_HEIGHT
                + (MAX_WINDY - mWindyList.get(i))*1.0/(MAX_WINDY - MIN_WINDY) * (MAX_WINDY_HEIGHT - MIN_WINDY_HEIGHT));
        int windyBottom = mHeight - mBottomTextHeight;
        return new Rect(windyLeft, windyTop, windyRight, windyBottom);
    }

    public Point getTemperaturePoint(int left, int i) {
        int height = (int) (MIN_TEMPERATURE_HEIGHT +
                (MAX_TEMPERATURE_VALUE - mTemperatureList.get(i)) * 1.0f /
                        (MAX_TEMPERATURE_VALUE - MIN_TEMPERATURE_VALUE)
                        * (MAX_TEMPERATURE_HEIGHT - MIN_TEMPERATURE_HEIGHT));
        return new Point(left, height);
    }

    private void initViewTools() {

        mTemperaturePaint = new Paint();
        mTemperaturePaint.setColor(Color.WHITE);
        mTemperaturePaint.setAntiAlias(true);
        mTemperaturePaint.setStrokeWidth(8);
        mTemperaturePaint.setStyle(Paint.Style.STROKE);

        mWindyBoxPaint = new Paint();
        mWindyBoxPaint.setColor(Color.BLUE);
        mWindyBoxPaint.setAntiAlias(true);
        mWindyBoxPaint.setStrokeWidth(5);
        mWindyBoxPaint.setAlpha(80);

        mLinePaint = new Paint();
        mLinePaint.setColor(new Color().WHITE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5);

        mTimeTextPaint = new Paint();
        mTimeTextPaint.setTextSize(DisplayUtil.sp2px(getContext(), 12));
        mTimeTextPaint.setColor(new Color().WHITE);
        mTimeTextPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<mItemInfoList.size(); i++) {
            Point tempPoint = mItemInfoList.get(i).tempPoint;
            int temperature = mItemInfoList.get(i).temperature;

            onDrawTemperaturePic(canvas, i, mItemInfoList.get(i));
            onDrawTemperaturePoint(canvas, tempPoint, temperature);
            onDrawTemperatureLine(canvas, i);

            onDrawWindyBox(canvas, i, mItemInfoList.get(i));
            onDrawTimeText(canvas, mItemInfoList.get(i));

            onDrawScrollSpan(canvas, i, mItemInfoList.get(i));
        }
    }

    private void onDrawScrollSpan(Canvas canvas, int i, HourItem hourItem) {
        if (i != calculateItemIndex()) {
            return;
        }
        int temperatureBarY = getTemperatureBarY();
        Drawable drawable = getResources().getDrawable(R.mipmap.hour_24_float);
        drawable.setBounds(calculateScrollX(),
                temperatureBarY - DisplayUtil.dip2px(getContext(), TEMPERATURE_PIC_TOP_MARGIN),
                calculateScrollX() + ITEM_WIDTH,
                temperatureBarY - DisplayUtil.dip2px(getContext(), TEMPERATURE_PIC_BOTTOM_MARGIN));
        drawable.draw(canvas);

        int currentIcon = getCurrentIconId(calculateItemIndex());
        if (currentIcon != -1) {
            Drawable temperaturePic = ContextCompat.getDrawable(getContext(), currentIcon);
            temperaturePic.setBounds(calculateScrollX() + ITEM_WIDTH / 2 + (ITEM_WIDTH / 2 - DisplayUtil.dip2px(getContext(), TEMPERATURE_PIC_WIDTH)) / 2,
                    getTemperatureBarY() - DisplayUtil.dip2px(getContext(), TEMPERATURE_PIC_TOP_MARGIN),
                    calculateScrollX() + ITEM_WIDTH - (ITEM_WIDTH / 2 - DisplayUtil.dip2px(getContext(), TEMPERATURE_PIC_WIDTH)) / 2,
                    getTemperatureBarY() - DisplayUtil.dip2px(getContext(), TEMPERATURE_PIC_BOTTOM_MARGIN));
            temperaturePic.draw(canvas);
        }

        Rect rect = new Rect(calculateScrollX(),
                temperatureBarY - DisplayUtil.dip2px(getContext(), TEMPERATURE_PIC_TOP_MARGIN),
                calculateScrollX() + ITEM_WIDTH - DisplayUtil.dip2px(getContext(), TEMPERATURE_PIC_WIDTH),
                temperatureBarY - DisplayUtil.dip2px(getContext(), TEMPERATURE_PIC_BOTTOM_MARGIN));

        mTimeTextPaint.setTextAlign(Paint.Align.CENTER);
        mTimeTextPaint.setColor(Color.WHITE);
        Paint.FontMetricsInt fontMetricsInt = mTimeTextPaint.getFontMetricsInt();
        int baseLine = (rect.top + rect.bottom - fontMetricsInt.top - fontMetricsInt.bottom)/2;

        canvas.drawText(hourItem.temperature + "", rect.centerX(), baseLine, mTimeTextPaint);

    }

    private void onDrawTemperaturePic(Canvas canvas, int index, HourItem hourItem) {
        if (mScrollIndex == index || hourItem.res == -1) {
            return;
        }
        Drawable temperaturePic = ContextCompat.getDrawable(getContext(), hourItem.res);
        temperaturePic.setBounds(
                hourItem.tempPoint.x - DisplayUtil.dip2px(getContext(), 10),
                hourItem.tempPoint.y - DisplayUtil.dip2px(getContext(), 25),
                hourItem.tempPoint.x + DisplayUtil.dip2px(getContext(), 10),
                hourItem.tempPoint.y - DisplayUtil.dip2px(getContext(), 5));
        temperaturePic.draw(canvas);
    }

    private void onDrawTimeText(Canvas canvas, HourItem hourItem) {
        Rect windyBoxRect = hourItem.windyBoxRect;
        Rect timeRect = new Rect(windyBoxRect.left, windyBoxRect.bottom, windyBoxRect.right, windyBoxRect.bottom + mBottomTextHeight);
        Paint.FontMetricsInt fontMetricsInt = mTimeTextPaint.getFontMetricsInt();
        int baseline = (timeRect.bottom + timeRect.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2;

        mTimeTextPaint.setTextAlign(Paint.Align.CENTER);
        mTimeTextPaint.setColor(Color.WHITE);
        // 如果直接写,文字会从text中间开始写,导致文字整体偏右
//        canvas.drawText(hourItem.time, timeRect.centerX(), baseline, mTimeTextPaint);
        canvas.drawText(hourItem.time, timeRect.centerX(), baseline, mTimeTextPaint);
    }


    private void onDrawTemperatureLine(Canvas canvas, int i) {
        if (i > (mItemInfoList.size() - 2)) {
            return;
        }
        mLinePaint.setColor(Color.YELLOW);
        mLinePaint.setStrokeWidth(3);
        Point prePoint = mItemInfoList.get(i).tempPoint;
        Point nextPoint = mItemInfoList.get(i + 1).tempPoint;
        Path linePaint = new Path();
        linePaint.moveTo(prePoint.x, prePoint.y);
        if(i % 2 == 0)
            linePaint.cubicTo((prePoint.x+nextPoint.x)/2, (prePoint.y+nextPoint.y)/2-7, (prePoint.x+nextPoint.x)/2, (prePoint.y+nextPoint.y)/2+7, nextPoint.x, nextPoint.y);
        else
            linePaint.cubicTo((prePoint.x+nextPoint.x)/2, (prePoint.y+nextPoint.y)/2+7, (prePoint.x+nextPoint.x)/2, (prePoint.y+nextPoint.y)/2-7, nextPoint.x, nextPoint.y);
        canvas.drawPath(linePaint, mLinePaint);
    }

    private void onDrawWindyBox(Canvas canvas, int i, HourItem hoursItem) {
        mWindyBoxPaint.setStyle(Paint.Style.FILL);
        mWindyBoxPaint.setColor(Color.WHITE);
        Rect windyRect = hoursItem.windyBoxRect;
        if (i == calculateItemIndex()) {
            mWindyBoxPaint.setAlpha(255);
            Rect rect = new Rect(windyRect.left,
                    windyRect.top - DisplayUtil.dip2px(getContext(), WINDY_TEXT_HEIGHT + 5),
                    windyRect.right,
                    windyRect.top - DisplayUtil.dip2px(getContext(), 5));
            mTimeTextPaint.setColor(Color.GREEN);
            mTimeTextPaint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetricsInt fontMetricsInt = mTimeTextPaint.getFontMetricsInt();
            int baseLine = (rect.top + rect.bottom - fontMetricsInt.top - fontMetricsInt.bottom)/2;
            canvas.drawText(hoursItem.windy + " 级风", rect.centerX(), baseLine, mTimeTextPaint);
        } else {
            mWindyBoxPaint.setAlpha(80);
        }
        canvas.drawRect(windyRect, mWindyBoxPaint);
    }

    private void onDrawTemperaturePoint(Canvas canvas, Point tempPoint, int temperature) {
        canvas.drawPoint(tempPoint.x, tempPoint.y, mTemperaturePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setScrollOffset(int offset, int maxScrollOffset) {
        mMaxOffset = maxScrollOffset;
        mRealScrollX = offset;
        mScrollIndex = calculateItemIndex();
        invalidate();
    }

    private int calculateItemIndex() {
        int x = calculateScrollX();
        int index=0;
        int sum = MARGIN_LEFT_ITEM - ITEM_WIDTH/2;
        for (index=0; index<ITEM_SIZE; index++) {
            sum += ITEM_WIDTH;
            if (sum > x) {
                return index;
            }
        }
        return ITEM_SIZE - 1;

    }

    private int calculateScrollX() {
        if (mRealScrollX <= 0 && mMaxOffset <= 0) {
            return 0;
        }
        int calculateX = (int) (((ITEM_SIZE-1) * ITEM_WIDTH) * 1.0 / mMaxOffset * mRealScrollX);
        return MARGIN_LEFT_ITEM + calculateX;
    }

    public String getItemTime(int i) {
        String time;
        if(i<10){
            time = "t0" + i + ":00";
        } else {
            time = "t" + i + ":00";
        }
        return time;
    }

    public int getTemperatureBarY() {
        int x = calculateScrollX();
        int proIndex;
        int sumLeft = MARGIN_LEFT_ITEM;

        Point proPoint = null;
        Point nextPoint;
        for (proIndex=0; proIndex<ITEM_SIZE; proIndex++) {
            sumLeft += ITEM_WIDTH;
            if (sumLeft > x) {
                proPoint = mItemInfoList.get(proIndex).tempPoint;
                break;
            }
        }

        // 如果是最后一个,直接返回最后一个温度的y
        if (proIndex >= ITEM_SIZE - 1 || proPoint == null) {
            return mItemInfoList.get(ITEM_SIZE-1).tempPoint.y;
        } else {
            nextPoint = mItemInfoList.get(proIndex + 1).tempPoint;
            Rect windyBoxRect = mItemInfoList.get(proIndex).windyBoxRect;
            return (int) (proPoint.y + (x - windyBoxRect.left) * 1.0 / ITEM_WIDTH * (nextPoint.y - proPoint.y));
        }
    }

    public int getCurrentIconId(int index) {
        if (mItemInfoList.get(index).res != -1) {
            return mItemInfoList.get(index).res;
        }
        for (int i=index; i>=0; i--) {
            if (mItemInfoList.get(i).res != -1) {
                return mItemInfoList.get(i).res;
            }
        }
        return -1;
    }


    private class HourItem {
        public String time; //时间点
        public Rect windyBoxRect; //表示风力的box
        public int windy; //风力
        public int temperature; //温度
        public Point tempPoint; //温度的点坐标
        public int res = -1; //图片资源(有则绘制)
    }
}
