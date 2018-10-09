package com.example.yooho.zerostart.ui.view.processbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 16/10/10.
 *
 * 主要的点:
 * 1、自定义xml配置属性
 * 2、在onMeasure时读取、设置长、宽
 * 3、画框,
 * 4、画进度颜色(save,clipRect,restore)5
 * 5、动态画滑动模块(动态计算left)
 * 6、动态画文字(先得到文字的rect,得到长宽,动态改变颜色,范围)paint.getTextBound
 *
 */
public class GlitterProcessBar extends View implements Runnable{

    private int mProcessTextSize;
    private int mLoadingColor;
    private int mStopColor;

    private Paint mProcessImagePaint;
    private Bitmap mProcessBitmap;
    private Canvas mProcessCanvas;
    private Rect mTextBount;

    private static int DEFAULT_HEIGHT = 35;
    public static int MAX_PROCESS = 100;
    private float mProcess;
    private int mStatus = 0;
    private float mGlitterLeft;

    public static final int STATUS_DOWNLOAD_PAUSE = 0;
    public static final int STATUS_DOWNLOADING = 1;
    public static final int STATUS_DOWNLOAD_FINISH = 2;

    private Bitmap mGlitterBitmap;
    PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    public GlitterProcessBar(Context context) {
        this(context, null);
    }
    public GlitterProcessBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlitterProcessBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributeValue(attrs);
    }

    private void getAttributeValue(AttributeSet attrs) {
        TypedArray typedArray = getContext().getResources().obtainAttributes(attrs, R.styleable.MyGlitterProcess);
        mProcessTextSize = (int) typedArray.getDimension(R.styleable.MyGlitterProcess_glitterTextSize, dp2px(12));
        mLoadingColor = typedArray.getColor(R.styleable.MyGlitterProcess_glitterLoadingColor, Color.parseColor("#40c4ff"));
        mStopColor = typedArray.getColor(R.styleable.MyGlitterProcess_glitterStopColor, Color.parseColor("#ff9800"));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightValue = 0;
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                heightValue = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                heightValue = (int) dp2px(DEFAULT_HEIGHT);
        }
        setMeasuredDimension(widthSize, heightValue);
        init();
    }

    private void init() {
        mProcessImagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGlitterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flicker);
        mProcessBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mProcessCanvas = new Canvas(mProcessBitmap);
        mTextBount = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画外框
        drawBorder(canvas);

        // 画进度
        drawableProcess();
        canvas.drawBitmap(mProcessBitmap, 0, 0, null);

        // 进度文字
        drawProcessText(canvas);

        // 改变字体颜色
        changeProcessText(canvas);
        
    }

    private void changeProcessText(Canvas canvas) {
        String changeText = getProcessText();
        mProcessImagePaint.getTextBounds(changeText, 0, changeText.length(), mTextBount);

        int width = mTextBount.width();
        int height = mTextBount.height();
        int left = (getMeasuredWidth() - width) / 2;
        int top = (getMeasuredHeight() + height) / 2;
        Log.e("SSS", getMeasuredHeight() + "  ::  " + height);
        float processLeft = mProcess / MAX_PROCESS * getMeasuredWidth();

        if (processLeft > left) {
            mProcessImagePaint.setColor(Color.WHITE);
            float right = Math.min(processLeft, left + width * 1.1f);
            canvas.save(Canvas.CLIP_SAVE_FLAG);
            canvas.clipRect(left, 0, right, getMeasuredHeight());
            canvas.drawText(changeText,left ,top, mProcessImagePaint);
            canvas.restore();
        }

    }

    private void drawProcessText(Canvas canvas) {
        String processText = getProcessText();
        mProcessImagePaint.setColor(mStopColor);
        mProcessImagePaint.setTextSize(mProcessTextSize);
        mProcessImagePaint.getTextBounds(processText, 0, processText.length(), mTextBount);
        int textWidth = mTextBount.width();
        int textHeight = mTextBount.height();
        float textX = (getMeasuredWidth() - textWidth) / 2;
        float textY = (getMeasuredHeight() + textHeight) / 2;
        canvas.drawText(processText, textX, textY, mProcessImagePaint);
    }

    private void drawableProcess() {
        mProcessImagePaint.setStyle(Paint.Style.FILL);
        mProcessImagePaint.setStrokeWidth(0);
        mProcessImagePaint.setColor(mLoadingColor);

        mProcessCanvas.save(Canvas.CLIP_SAVE_FLAG);
        mProcessCanvas.clipRect(0, 0, mProcess*getMeasuredWidth()/MAX_PROCESS, getHeight());
        mProcessCanvas.drawColor(mLoadingColor);
        mProcessCanvas.restore();

        if (mStatus == STATUS_DOWNLOADING) {
            mProcessImagePaint.setXfermode(xfermode);
            mProcessCanvas.drawBitmap(mGlitterBitmap, mGlitterLeft, 0, mProcessImagePaint);
            mProcessImagePaint.setXfermode(null);
        }

    }

    private void drawBorder(Canvas canvas) {
        mProcessImagePaint.setStyle(Paint.Style.STROKE);
        mProcessImagePaint.setStrokeWidth(1);
        mProcessImagePaint.setColor(mLoadingColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mProcessImagePaint);
    }

    public void setStop() {
        mStatus = STATUS_DOWNLOAD_PAUSE;
        invalidate();
    }

    public void setRestart() {
        mStatus = STATUS_DOWNLOADING;
        new Thread(this).start();
        invalidate();
    }

    public void setProcess(int process) {
        process = process < 0 ? 0 : process;
        process = process > MAX_PROCESS ? MAX_PROCESS : process;
        if (process < MAX_PROCESS) {
            mStatus = STATUS_DOWNLOADING;
        } else if (process >= MAX_PROCESS) {
            mStatus = STATUS_DOWNLOAD_FINISH;
        }
        mProcess = process;
        invalidate();
    }

    private float dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return dp * density;
    }


    @Override
    public void run() {
        while (mStatus == STATUS_DOWNLOADING){
            mGlitterLeft += dp2px(5);
            float progressWidth = (mProcess / MAX_PROCESS) * getMeasuredWidth();
            if(mGlitterLeft >= progressWidth){
                mGlitterLeft = -mGlitterBitmap.getWidth();
            }
            postInvalidate();
            try {
                long sleep = 300;
                if (mProcess<20) {
                    sleep = 70;
                } else if (mProcess< 40) {
                    sleep = 50;
                } else if (mProcess < 60) {
                    sleep = 30;
                }else {
                    sleep = 20;
                }
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getProcessText() {
        switch (mStatus) {
            case STATUS_DOWNLOAD_PAUSE:
                return "暂停";
            case STATUS_DOWNLOADING:
                return "下载了" + mProcess;
            case STATUS_DOWNLOAD_FINISH:
                return "已完成";
        }
        return "未知状况";
    }
}
