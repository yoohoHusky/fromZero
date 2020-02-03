package com.example.yooho.zerostart.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenTools {
    public static int dip2Px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //获取屏幕的宽度
    public static int getScreenWidth(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(displayMetrics);
        } else {
            display.getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(displayMetrics);
        } else {
            display.getMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    /**
     * 获取虚拟功能键高度
     */
    public static int getNavigationBarHeight(Activity activity) {
        int rid = activity.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return activity.getResources().getDimensionPixelSize(resourceId);
        } else return 0;
    }
}
