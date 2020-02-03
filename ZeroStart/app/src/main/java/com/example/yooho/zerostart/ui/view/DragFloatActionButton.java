package com.example.yooho.zerostart.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import static android.content.Context.WINDOW_SERVICE;

public class DragFloatActionButton extends android.support.v7.widget.AppCompatImageView {

    private int parentHeight;
    private int parentWidth;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setAlpha(0.3f);
        }
    };




    private int lastX;
    private int lastY;

    private boolean isDrag;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWm;
//    private ViewGroup parent;

    public DragFloatActionButton(Context context) {
        super(context);
        initView(context);
    }

    public DragFloatActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DragFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

//        WindowManager wm = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
        mWm = (WindowManager)context.getApplicationContext().getSystemService(WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        Display display = mWm.getDefaultDisplay();
        parentWidth = display.getWidth();
        parentHeight = display.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#FF0000"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(width, 800);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                this.setAlpha(0.9f);
                setPressed(true);
                isDrag = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
//                if (getParent() != null) {
//                    parentHeight = parent.getHeight();
//                    parentWidth = parent.getWidth();
//                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (parentHeight <= 0.2 || parentWidth <= 0.2) {
                    isDrag = false;
                    break;
                } else {
                    isDrag = true;
                }
                this.setAlpha(0.9f);
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                //这里修复一些华为手机无法触发点击事件
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (distance == 0) {
                    isDrag = false;
                    break;
                }
                float x = getX() + dx;
                float y = getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > parentWidth - getWidth() ? parentWidth - getWidth() : x;
                y = getY() < 0 ? 0 : getY() + getHeight() > parentHeight ? parentHeight - getHeight() : y;
                updateXY(x, y);
//                setX(x);
//                setY(y);
                lastX = rawX;
                lastY = rawY;
                Log.i("aa", "isDrag=" + isDrag + "getX=" + getX() + ";getY=" + getY() + ";parentWidth=" + parentWidth);
                break;
            case MotionEvent.ACTION_UP:
                if (!isNotDrag()) {
                    //恢复按压效果
                    setPressed(false);
                    //Log.i("getX="+getX()+"；screenWidthHalf="+screenWidthHalf);
                    moveHide(rawX);
                } else {
                    myRunable();
                }
                break;
        }
        //如果是拖拽则消s耗事件，否则正常传递即可。
        return !isNotDrag() || super.onTouchEvent(event);
    }

    private void updateXY(float x, float y) {
        mLayoutParams.x = (int) x;
        mLayoutParams.y = (int) y;
        mWm.updateViewLayout(this, mLayoutParams);
    }


    private boolean isNotDrag() {
        return !isDrag && (getX() == 0
                || (getX() == parentWidth - getWidth()));
    }

    private void moveHide(int rawX) {
        if (rawX >= parentWidth / 2) {
            //靠右吸附
            animate().setInterpolator(new DecelerateInterpolator())
                    .setDuration(500)
                    .xBy(parentWidth - getWidth() - getX())
                    .start();
            myRunable();
        } else {
            //靠左吸附
            ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", getX(), 0);
            oa.setInterpolator(new DecelerateInterpolator());
            oa.setDuration(500);
            oa.start();
            myRunable();

        }
    }

    private void myRunable() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 2000);
    }




}
