package com.example.yooho.zerostart.ui.view.bitmap.splite;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.yooho.zerostart.R;

import java.util.ArrayList;
import java.util.List;

public class SplitBitmap extends View {

    private Bitmap src;
    private Paint paint;
    private int imageWidth;
    private int imageHeigh;
    private List<Ball> ballList;
    private ValueAnimator mAnimator;
//    private int[] colorArr;

    private int d = 4;
    private long mRunTime;


    public SplitBitmap(Context context) {
        this(context, null);
    }
    public SplitBitmap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTools();
        initData();
        initAnimation();
    }

    private void initAnimation() {
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setRepeatCount(-1);
        mAnimator.setDuration(2000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(animation -> {
            updateBall();//更新小球位置
            invalidate();
        });
    }

    private void updateBall() {
        for (int i = 0; i < ballList.size(); i++) {
            Ball ball = ballList.get(i);
            if (System.currentTimeMillis() - mRunTime > 2000) {
                ballList.remove(i);
            }
            ball.x += ball.vX;
            ball.y += ball.vY;
            ball.vY += ball.aY;
            ball.vX += ball.aX;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 600);
    }

    //    public void saveImage(String path , int[] intArr) {
//        getContext().getExternalCacheDir();
//        File desFile = new File(getContext().getCacheDir(), path + "png");
//        try {
//            FileOutputStream fos = new FileOutputStream(desFile);
//
//            fos.write();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRunTime = System.currentTimeMillis();//记录点击时间
                mAnimator.start();
                break;
        }
        return true;
    }



    private void initData() {
        ballList = new ArrayList<>();

        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeigh; j++) {
//                colorArr[imageWidth * i + j] = src.getPixel(i, j);
                Log.e("SS", src.getPixel(i, j) + "");
                if (src.getPixel(i, j) < 0) {
                    Ball ball = new Ball();
                    ball.x = i * d + d /2;
                    ball.y = j * d + d /2;
                    ball.vX = (float) (Math.pow(-1, Math.ceil(Math.random() * 2)) * 20 * Math.random());
                    ball.vY = (float) (Math.random() * 50 - 15);
                    ball.aY = 0.98f;
                    ball.color = src.getPixel(i, j);
                    ball.born = System.currentTimeMillis();
                    ballList.add(ball);
                }
            }
        }
    }

    private void initTools() {
        paint = new Paint();

        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        ops.inSampleSize = 2;
        ops.inJustDecodeBounds = false;
        src = BitmapFactory.decodeResource(getResources(), R.mipmap.bye, ops);
        imageWidth = src.getWidth();
        imageHeigh = src.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(src, 10, 10, paint);
//        canvas.drawBitmap(src, imageWidth + 20, 10, paint);

        canvas.save();
        for (Ball ball : ballList) {
            paint.setColor(ball.color);
            canvas.drawCircle(ball.x, ball.y + imageHeigh + 20, d / 2, paint);
        }
        canvas.restore();
    }
}
