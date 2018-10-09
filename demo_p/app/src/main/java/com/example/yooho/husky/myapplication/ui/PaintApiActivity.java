package com.example.yooho.husky.myapplication.ui;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.yooho.husky.myapplication.R;

/**
 * Created by haoou on 2018/8/5.
 */

public class PaintApiActivity extends AppCompatActivity{


    /**
     *
     * 		1. getLocationOnScreen()
     2. getRawX(), getRawY()


     1. getX（）， getY（）
     2. getLeft（）， getTop（），
     3. getMarginLeft（）:无法从view中获得，只能从其layout中获得，并且只有relativeLayout才有。
     4. getPaddingLeft（）（获取自己的padding）
     *
     *
     *
     * 1. getLocalVisibleRect()
     2. getGlobalVisibleRect()
     3. getLocationOnScreen()
     4. getLocationInWindow()
     *
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_paint_api);





        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showViewData(R.id.root);
                showViewData(R.id.view_model_1);
                showViewData(R.id.view_model_2);
                showViewData(R.id.view_model_3);

                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(displayMetrics);
                Log.e("divv", displayMetrics.density + "");
                Log.e("divv", displayMetrics.densityDpi + "");
                Log.e("divv", displayMetrics.heightPixels + "");
                Log.e("divv", displayMetrics.widthPixels + "");

            }
        }, 3000);
    }

    private void showViewData(int resId) {
        int[] ints = new int[2];
        int[] ints2 = new int[2];
        Rect rect = new Rect();
        Rect rect1 = new Rect();

        View model1 = findViewById(resId);
        model1.getLocalVisibleRect(rect);
        model1.getGlobalVisibleRect(rect1);

        model1.getLocationOnScreen(ints);
        model1.getLocationInWindow(ints2);



        Log.e("divv", "getX:" + model1.getX());
        Log.e("divv", "getLeft:" + model1.getLeft());
        Log.e("divv", "getTranslationX:" + model1.getTranslationX());
        Log.e("divv", "getPaddingLeft:" + model1.getPaddingLeft());
        Log.e("divv", "getLocalVisibleRect:" + rect.left + " : " + rect.right);
        Log.e("divv", "getGlobalVisibleRect:" + rect1.left + " : " + rect1.right);
        Log.e("divv", "getGlobalVisibleRect:" + rect1.top + " : " + rect1.bottom);
        Log.e("divv", "getLocationOnScreen:" + ints[0] + "  :  " + ints[1]);
        Log.e("divv", "getLocationInWindow:" + ints[0] + "  :  " + ints[1]);

        Log.e("divv", "__________________________");


    }
}
