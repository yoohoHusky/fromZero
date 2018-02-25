package com.example.yooho.zerostart.ui.view.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by haoou on 2018/2/11.
 */

public class DemoSurfaceView extends SurfaceView {
    public DemoSurfaceView(Context context) {
        super(context);
    }

    public DemoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    SurfaceHolder.Callback cb = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };


}
