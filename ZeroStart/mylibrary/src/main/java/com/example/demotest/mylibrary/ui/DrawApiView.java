package com.example.demotest.mylibrary.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.demotest.mylibrary.R;

public class DrawApiView extends View {
    private int DEFAULT_HEIGHT = 30;
    private int DEFAULT_BOX_HEIGHT = 50;
    private int DEFAULT_SPACE_HEIGHT = 10;

    private Paint mBoxPaint;
    private Paint mTextPaint;
    private Paint mBgPaint;
    private Paint mGlitterPaint;
    private Paint mFlickerPaint;

    public DrawApiView(Context context) {
        this(context, null);
    }

    public DrawApiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawApiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTools();
    }

    private void initTools() {
        mBoxPaint = new Paint();
        mBgPaint = new Paint();
        mTextPaint = new Paint();
        mGlitterPaint = new Paint();
        mFlickerPaint = new Paint();
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
                heightValue = DEFAULT_BOX_HEIGHT * 2 + DEFAULT_SPACE_HEIGHT * 1;
        }
        setMeasuredDimension(widthSize, heightValue);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBox(canvas);
        drawColorBg(canvas);
        overDrawColorBg(canvas);
        drawPicture(canvas);
        drawOtherImage(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextSize(48);

        // 用画笔测量出文字的区域，传入个rect
        String str = "测试内容ABCDEFG，变色字体";
        Rect strRect = new Rect();
        mTextPaint.getTextBounds(str, 0, str.length(), strRect);
        int strWidth = strRect.width();
        int strHeight = strRect.height();

        // drawText直接画文字
        mTextPaint.setColor(Color.DKGRAY);
        canvas.drawText(str, (getWidth() - strWidth) / 2, getHeight() / 2 + (getHeight() / 2 + strHeight) / 2, mTextPaint);

        // 剪切出不一样颜色的部分，drawText文字
        mTextPaint.setColor(Color.RED);
        canvas.save();
        canvas.clipRect((getWidth() - strWidth) / 2, getHeight() / 2, (getWidth() - strWidth) / 2 + 300, getHeight());
        canvas.drawText(str, (getWidth() - strWidth) / 2, getHeight() / 2 + (getHeight() / 2 + strHeight) / 2, mTextPaint);
        canvas.restore();
    }

    private void drawOtherImage(Canvas canvas) {

        // 创建一个bitmap，将bitmap放进画布里
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth()*3/8, getMeasuredHeight()/2, Bitmap.Config.ARGB_8888);
        Canvas otherCanvas = new Canvas(bitmap);
        otherCanvas.drawColor(Color.RED);

        // 在画布上作画
        otherCanvas.save();
        otherCanvas.clipRect(0, 0, getWidth()/4, getHeight()/2);
        otherCanvas.drawColor(Color.GRAY);
        otherCanvas.restore();

        // 画浮在上面的交集
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        mFlickerPaint.setXfermode(xfermode);
        Bitmap flicker = BitmapFactory.decodeResource(getResources(), R.drawable.flicker);
        otherCanvas.drawBitmap(flicker, -30, 0, mFlickerPaint);
        mFlickerPaint.setXfermode(null);

        // 然后在主canvas上画bitmap
        canvas.drawBitmap(bitmap, getWidth()/2, DEFAULT_BOX_HEIGHT + DEFAULT_SPACE_HEIGHT, null);
    }

    private void drawPicture(Canvas canvas) {

    }

    private void overDrawColorBg(Canvas canvas) {
        // 先将需要绘画的区域抠出来，设置上边界大小，表示允许作画的区域就只能这么大
        Rect rect = new Rect(30,DEFAULT_BOX_HEIGHT + DEFAULT_SPACE_HEIGHT, getWidth()/4, getHeight());
        canvas.save();
        canvas.clipRect(rect);

        // 然后在抠出来的区域作画，画的再大都没有问题
        canvas.drawColor(Color.BLUE);

        // 将抠出来的部分加回去
        canvas.restore();
    }

    private void drawColorBg(Canvas canvas) {
        canvas.drawRect(0, DEFAULT_BOX_HEIGHT + DEFAULT_SPACE_HEIGHT, getWidth(), DEFAULT_BOX_HEIGHT * 2 + DEFAULT_SPACE_HEIGHT, mBoxPaint);

        // 设置好style，画实心，然后drawRect，设置好范围边界就可以了
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, DEFAULT_BOX_HEIGHT + DEFAULT_SPACE_HEIGHT, getWidth()/2, DEFAULT_BOX_HEIGHT * 2 + DEFAULT_SPACE_HEIGHT, mBgPaint);

        mBgPaint.setColor(Color.GREEN);
        canvas.drawRect(getWidth()/2, DEFAULT_BOX_HEIGHT + DEFAULT_SPACE_HEIGHT, getWidth(), DEFAULT_BOX_HEIGHT * 2 + DEFAULT_SPACE_HEIGHT, mBgPaint);

    }

    private void drawBox(Canvas canvas) {
        mBoxPaint.setFlags(Paint.ANTI_ALIAS_FLAG);  // 文件里说是设置抗锯齿，实际测是完全展示出右、下边框
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setColor(Color.RED);
        mBoxPaint.setStrokeWidth(1);
        canvas.drawRect(0,0, getWidth(), DEFAULT_BOX_HEIGHT, mBoxPaint);
    }


    /****************     ********************/

    private float dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return dp * density;
    }

}
