package com.example.yooho.husky.myapplication.ui.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by haoou on 2018/8/9.
 */

public class SimpleLayout extends RelativeLayout {
    public SimpleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        Log.e("SS", l + " | " + t + " | " + r + " | " + b);
        if (getChildCount() > 0) {
            View child = getChildAt(0);
//            child.layout(100, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            child.layout(100, 0, 980, child.getMeasuredHeight());
        }
//        super.onLayout(changed, l, t, r, b);
    }
}
