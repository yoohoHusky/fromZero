package com.example.yooho.zerostart.ui.view.weather;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

import com.example.yooho.zerostart.tools.DisplayUtil;

/**
 * Created by yooho on 2016/11/5.
 */
public class WeatherScrollView extends HorizontalScrollView {

    OneDay24HourView mOneDay24HourView;

    public WeatherScrollView(Context context) {
        this(context, null);
    }

    public WeatherScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        textPaint = new Paint();
//        textPaint.setTextSize(DisplayUtil.sp2px(getContext(), 12));
//        textPaint.setAntiAlias(true);
//        textPaint.setColor(new Color().WHITE);
//
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int offset = computeHorizontalScrollOffset();
        int scrollX = getScrollX();
        int maxOffset = computeHorizontalScrollRange() - DisplayUtil.getScreenWidth(getContext());
        if(mOneDay24HourView != null){
//            today24HourView.drawLeftTempText(canvas, offset);
            mOneDay24HourView.setScrollOffset(offset, maxOffset);
        }
    }

    public void setOneDay24HourView(OneDay24HourView today24HourView){
        this.mOneDay24HourView = today24HourView;
    }
}
