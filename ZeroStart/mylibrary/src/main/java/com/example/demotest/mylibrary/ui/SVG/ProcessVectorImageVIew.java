package com.example.demotest.mylibrary.ui.SVG;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.example.demotest.mylibrary.R;

import java.util.regex.Pattern;

public class ProcessVectorImageVIew extends View {

    // 构造属性成员
    private String borderPath;
//    private String toPathStr;

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
    private Paint mStrokePaint;
    private Paint mWaterPaint;
    private ObjectAnimator onceObjAnim;
    private ObjectAnimator recycleObjAnim;

    private SVGPathSingleObject borderSVG;
    private SVGPathSingleObject waterSVG;
    private SVGPathObject clipSVG;

    // 状态标识符
    private boolean isReverse = false;
    private String clipPath1;
    private String clipPath2;
    ;


    public ProcessVectorImageVIew(Context context) {
        this(context, null);
    }

    public ProcessVectorImageVIew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProcessVectorImageVIew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttrs(context, attrs);
//        parseProperties(attrs);
        initTools();
    }


    public void enableRotate(boolean enableRotate) {
        this.enableRotate = enableRotate;
    }

    public void updateFraction(float fraction) {
        setFraction(fraction);
    }




    /******************************/

    private void loadAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SVGControlImageView);
        portWidth = a.getInt(R.styleable.SVGControlImageView_viewPortWidth, VIEW_PORT_WIDTH);
        portHeight = a.getInt(R.styleable.SVGControlImageView_viewPortHeight, VIEW_PORT_HEIGHT);
        myColor = a.getColor(R.styleable.SVGControlImageView_fillColor, DEFAULT_FILL_COLOR);
        borderPath = a.getString(R.styleable.SVGControlImageView_pathStr_1);
        clipPath1 = a.getString(R.styleable.SVGControlImageView_pathStr_2);
        clipPath2 = a.getString(R.styleable.SVGControlImageView_pathStr_3);
        duration = a.getInt(R.styleable.SVGControlImageView_animDuration, DEFAULT_ANIM_DURATION);

        a.recycle();
    }

    private void initTools() {
        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(15);
        mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        mStrokePaint.setColor(Color.parseColor("#fa6262"));

        mWaterPaint = new Paint();
        mWaterPaint.setStyle(Paint.Style.FILL);
        mWaterPaint.setStrokeWidth(45);
        mWaterPaint.setColor(Color.parseColor("#00bcd4"));
    }

    private void setFraction(float fraction) {
        borderSVG.setFraction(fraction);
        clipSVG.setFraction(fraction);
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
//        svgObj = new SVGPathObject(fromPathStr, toPathStr, scale);
        borderSVG = new SVGPathSingleObject(borderPath, scale);
        waterSVG = new SVGPathSingleObject(borderPath, scale);
        clipSVG = new SVGPathObject(clipPath1, clipPath2, scale);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path borderPath = borderSVG.getPath();
        Path waterPath = borderSVG.getSrcPath();
        Path clipPath = clipSVG.getPath();

//        Path newPath2 = new Path();
//        Path newPath = new Path();
//        newPath2.addRect(0,0, 200, 200, Path.Direction.CCW);
//        newPath.addRect(0,0, 100, 100, Path.Direction.CCW);

//        waterPath.op(clipPath, Path.Op.INTERSECT);
//        canvas.drawPath(waterPath, mWaterPaint);


        canvas.save();
        canvas.clipPath(clipPath);
        canvas.drawPath(waterPath, mWaterPaint);
        canvas.restore();

        canvas.drawPath(borderPath, mStrokePaint);
    }


}
