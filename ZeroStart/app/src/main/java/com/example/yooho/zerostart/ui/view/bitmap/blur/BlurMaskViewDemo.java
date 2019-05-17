package com.example.yooho.zerostart.ui.view.bitmap.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.yooho.zerostart.R;

public class BlurMaskViewDemo extends View {
    Paint mPaint;
    Paint mPaint2;
    Bitmap bitmap;
    Bitmap zoomBbitmap;
    private static int increaseHeight = 800;
    boolean isTouchFirst;


    public BlurMaskViewDemo(Context context) {
        this(context, null);
    }

    public BlurMaskViewDemo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BlurMaskViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTools(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(width, 1700);
    }

    private void initTools(Context context) {
        mPaint = new Paint();
        mPaint2 = new Paint();
        mPaint2.setTextSize(24);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (bitmap == null) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ez);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.ez, opts);
        int outWidth = opts.outWidth;
        int outHeight = opts.outHeight;

        opts.inSampleSize = 2;
        opts.inJustDecodeBounds = false;
        Bitmap smallBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.ez, opts);



        if (zoomBbitmap == null) {
            Matrix matrix = new Matrix();
            matrix.postScale(1.05f, 1.05f);
            zoomBbitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        Log.e("SS", "isTouchFirst " + isTouchFirst);
        if (isTouchFirst) {
            Bitmap grayBitmap = zoomBbitmap.extractAlpha();
            mPaint.setColor(Color.BLUE);
            mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
            canvas.drawBitmap(grayBitmap, 50,  50, mPaint);
            canvas.drawBitmap(zoomBbitmap, 50, 50, null);
        } else {
            mPaint.setMaskFilter(null);
            canvas.drawBitmap(smallBitmap, 50, 50,  null);
        }
        canvas.drawText("null", 50 + bitmap.getWidth() + 50, 50, mPaint2);

        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
        canvas.drawBitmap(bitmap, 50, (bitmap.getHeight() + 100) * 1, mPaint);
        canvas.drawText("SOLID", 50 + bitmap.getWidth() + 50, (bitmap.getHeight() + 100) * 1 + 100, mPaint2);

        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
        canvas.drawBitmap(bitmap, 50, (bitmap.getHeight() + 100) * 2, mPaint);
        canvas.drawText("NORMAL", 50 + bitmap.getWidth() + 50, (bitmap.getHeight() + 100) * 2 + 100, mPaint2);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Log.e("SS", "ACTION_DOWN: " + action);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.e("SS", "ACTION_DOWN");
                float touchX = event.getX();
                float touchY = event.getY();
                boolean inX = 0 < touchX - 50 && touchX -50 < bitmap.getWidth();
                boolean inY = 0 < touchY - 50 && touchY -50 < bitmap.getHeight();
                isTouchFirst = inX && inY;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isTouchFirst = false;
                break;
        }
        invalidate();
        return true;
    }

}
