package com.example.demotest.mylibrary.ui.SVG;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.example.demotest.mylibrary.R;

public class ControllableVectorImageVIew extends View {

    // 构造属性成员
    private String fromPathStr;
    private String toPathStr;

    // 配置属性成员
    private float portWidth;
    private float portHeight;
    private int myColor;
    private int duration;
    private boolean enableRotate;

    // 配置属性默认项
    private final int DEFAULT_FILL_COLOR = Color.parseColor("#47a4ec");
    private final int VIEW_PORT_WIDTH = 100;
    private final int VIEW_PORT_HEIGHT = 100;
    private final int DEFAULT_ANIM_DURATION = 1500;
    private final String DEFAULT_FROM_PATH = "M99,349 C193,240,283,165,400,99 C525,172,611,246,701,348 C521,416,433,511,400,700 C356,509,285,416,99,349";
    private final String DEFAULT_TO_PATH = "M99,349 C297,346,376,210,400,99 C432,208,506,345,701,348 C629,479,549,570,400,700 C227,569,194,522,99,349";

    // 工具成员
    private Paint mPaint;
    private Path mPath;
    private ObjectAnimator onceObjAnim;
    private ObjectAnimator recycleObjAnim;
    private SVGPathObject svgObj;
    private VectorImageStateListener mListener;

    // 状态标识符
    private boolean isReverse = false;


    public ControllableVectorImageVIew(Context context) {
        this(context, null);
    }

    public ControllableVectorImageVIew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControllableVectorImageVIew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttrs(context, attrs);
//        parseProperties(attrs);
        initTools();
    }

    public void setStateListener(VectorImageStateListener listener) {
        mListener = listener;
    }

    public void enableRotate(boolean enableRotate) {
        this.enableRotate = enableRotate;
    }

    public void updateFraction(float fraction) {
        if (onceObjAnim != null) onceObjAnim.cancel();
        if (recycleObjAnim != null) recycleObjAnim.cancel();

        isReverse = false;
        setFraction(fraction);
    }

    public void executeOnceAnim() {
        if (onceObjAnim == null) {
            onceObjAnim = ObjectAnimator.ofFloat(this, "fraction", 0f, 1f);
            onceObjAnim.setDuration(duration);
            onceObjAnim.addListener(new OnceAnimListener());
        }
        if (!isAnimRunning()) {
            if (isReverse) {
                onceObjAnim.reverse();
            } else {
                onceObjAnim.start();
            }
        } else if (recycleObjAnim != null && recycleObjAnim.isRunning()){
            recycleObjAnim.cancel();
            onceObjAnim.start();
        }
    }

    @SuppressLint("WrongConstant")
    public void executeRecycleAnim() {
        if (recycleObjAnim == null) {
            recycleObjAnim = ObjectAnimator.ofFloat(this, "fraction", 0f, 1f);
            recycleObjAnim.setDuration(duration);
            recycleObjAnim.setRepeatCount(Animation.INFINITE);
            recycleObjAnim.setRepeatMode(Animation.REVERSE);
        }
        if (!isAnimRunning()) {
            isReverse = false;
            recycleObjAnim.start();
        }
    }

    public void onRelease() {
        if (onceObjAnim != null) onceObjAnim.cancel();
        if (recycleObjAnim != null) recycleObjAnim.cancel();
        if (svgObj != null) svgObj.onRelease();

        onceObjAnim = null;
        recycleObjAnim = null;
        mPaint = null;
        mListener = null;
        svgObj = null;
    }



    /******************************/

    private void loadAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SVGControlImageView);
        portWidth = a.getInt(R.styleable.SVGControlImageView_viewPortWidth, VIEW_PORT_WIDTH);
        portHeight = a.getInt(R.styleable.SVGControlImageView_viewPortHeight, VIEW_PORT_HEIGHT);
        myColor = a.getColor(R.styleable.SVGControlImageView_fillColor, DEFAULT_FILL_COLOR);
        fromPathStr = a.getString(R.styleable.SVGControlImageView_pathStr_1);
        toPathStr = a.getString(R.styleable.SVGControlImageView_pathStr_2);
        duration = a.getInt(R.styleable.SVGControlImageView_animDuration, DEFAULT_ANIM_DURATION);

        if (TextUtils.isEmpty(fromPathStr)) fromPathStr = DEFAULT_FROM_PATH;
        if (TextUtils.isEmpty(toPathStr)) toPathStr = DEFAULT_TO_PATH;
        a.recycle();
    }

    private void initTools() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(myColor);
    }

    private void parseProperties(AttributeSet attrs) {

    }

    private void setFraction(float fraction) {
        svgObj.setFraction(fraction);
        if (enableRotate) setRotation(360 * fraction);
        invalidate();
    }

    private boolean isAnimRunning() {
        if (onceObjAnim != null && onceObjAnim.isRunning()) {
            return true;
        } else if (recycleObjAnim != null && recycleObjAnim.isRunning()) {
            return true;
        }
        return false;
    }

    private class OnceAnimListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mListener != null) mListener.stateCompleted(isReverse ? VectorImageStateListener.CODE_STATE_2_COMPLETED : VectorImageStateListener.CODE_STATE_1_COMPLETED);
            isReverse = !isReverse;
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        float portScale = portHeight / portWidth;
        float viewScale = 1.0f * height / width;

        if (portScale == viewScale) {
        } else if (portScale > viewScale) {
            width = (int) (height / portScale);
        } else if (portScale < viewScale) {
            height = (int) (width * portScale);
        }
        setMeasuredDimension(width, height);
        float scale = Math.min(width / portWidth, height / portHeight);
        svgObj = new SVGPathObject(fromPathStr, toPathStr, scale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath = svgObj.getPath();
        canvas.drawPath(mPath, mPaint);
    }

    // onceAnim状态改变的回调，其他anim不执行
    public static abstract class VectorImageStateListener {
        public static final int CODE_STATE_1_COMPLETED = 1;
        public static final int CODE_STATE_2_COMPLETED = 2;

        abstract void stateCompleted(int stateNum);
    }



}
