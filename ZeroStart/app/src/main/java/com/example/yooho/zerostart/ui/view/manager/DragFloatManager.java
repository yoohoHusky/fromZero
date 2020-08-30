package com.example.yooho.zerostart.ui.view.manager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.constant.SpKeyConstant;
import com.example.yooho.zerostart.tools.Miui;
import com.example.yooho.zerostart.tools.SpTools;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import static android.content.Context.WINDOW_SERVICE;

public class DragFloatManager {

    private static final int INIT_LOCATION_Y = 300;
    private static final int TIME_LOST_FOCUS_DELAY = 4000;
    private static final int TIME_ALIGN_BORDER_DELAY = 100;
    private static final int TIME_DRAG_MIN = 100;      // 认为up、down之间的时间少于300，就认为是在click，超过300就是在移动了

    private static DragFloatManager mInst;
    private WeakReference<Context> mContextRef;
    private View mRootView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private MyRootTouchListener mTouchListener;

    private int lastX;
    private int lastY;
    private int mMinX;  // 最左边x值，0
    private int mMaxX;
    private int mMinY;
    private int mMaxY;
    private int screenWidth;
    private int screenHeight;
    private Handler mHandler;
    private long downTime;
    private Runnable mDelayAlignBorder;
    private Runnable mDelayNofityCompress;
    private HashMap<String, FloatViewWatcher> mWatcherMap;

    public static DragFloatManager getInst() {
        if (mInst == null) {
            synchronized (DragFloatManager.class) {
                if (mInst == null) {
                    mInst = new DragFloatManager();
                }
            }
        }
        return mInst;
    }

    public void init(Context context) {
        if (mContextRef != null && mContextRef.get() != null) return;
        mContextRef = new WeakReference(context);
        initWindowParams(context);
        mTouchListener = new MyRootTouchListener();
        initHandler();
        checkRequestPermission();
    }

    /**
     * 向manager中注册监听
     * @param tag       注册watcher对应的名字，用于判断覆盖添加，移除时作用
     * @param watcher   监听器
     * @return          是否添加成功
     */
    public boolean registerFloatViewWatcher(String tag, FloatViewWatcher watcher) {
        if (mWatcherMap == null) mWatcherMap = new HashMap();

        if (mWatcherMap.containsKey(tag)) return false; // key名字已被占用，不能覆盖添加，添加失败
        mWatcherMap.put(tag, watcher);
        return true;
    }

    /**
     *
     * @param tag
     * @return  >=0,表示不再继续监听，0-表示map之前就没监听；1-表示从map中删除成功; <0-表示删除不成功，-1-表示不包含这个key
     */
    public int unregisterFloatWatcher(String tag) {
        if (mWatcherMap == null || mWatcherMap.isEmpty()) return 0;
        if (mWatcherMap.containsKey(tag)) {
            mWatcherMap.remove(tag);
            return 1;
        } else {
            return -1;
        }
    }

    public void setView(View view) {
        mRootView = view;
    }

    public void showFloatView() {
        if (!checkRequestPermission()) return;
        handleRootView();
        mWindowManager.addView(mRootView, mLayoutParams);
        mHandler.postDelayed(mDelayNofityCompress, TIME_LOST_FOCUS_DELAY);
    }

    public void dismissFloatView() {
        if (mContextRef == null) return;
        savePositionData();
        mWindowManager.removeView(mRootView);
    }


    /*  内部方法  */
    // 设置layout参数

    private DragFloatManager() {}

    private void initWindowParams(Context context) {
        mWindowManager = (WindowManager)context.getApplicationContext().getSystemService(WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        setParamsType(context.getApplicationContext(), mLayoutParams);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = SpTools.getInst().getInt(SpKeyConstant.KEY_DRAG_FLOAT_LOCATION_X, 0);
        mLayoutParams.y = SpTools.getInst().getInt(SpKeyConstant.KEY_DRAG_FLOAT_LOCATION_Y, INIT_LOCATION_Y);

        // 屏幕像素参数
        Display display = mWindowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
    }
    // 创建handler

    private void initHandler() {
        mHandler = new Handler();
        mDelayNofityCompress = () -> {
            notifyLostFocus();
            mHandler.postDelayed(mDelayAlignBorder, TIME_ALIGN_BORDER_DELAY);
        };
        mDelayAlignBorder = () -> alignBorder();
    }

    private void notifyLostFocus() {
        if (mWatcherMap == null || mWatcherMap.isEmpty()) return;
        for (FloatViewWatcher watcher : mWatcherMap.values()) {
            watcher.onLostFocus();
        }
    }

    private void notifyGetFocus() {
        if (mWatcherMap == null || mWatcherMap.isEmpty()) return;
        for (FloatViewWatcher watcher : mWatcherMap.values()) {
            watcher.onGetFocus();
        }
    }


    // 是否已获得对应的权限
    private boolean checkRequestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(mContextRef.get())) { //若未授权则请求权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + mContextRef.get().getPackageName()));
                mContextRef.get().startActivity(intent);
//                mContextRef.get().startActivityForResult(intent, 0);
                return false;
            }
        }
        return true;
    }

    private void handleRootView() {
        if (mRootView != null) mRootView = View.inflate(mContextRef.get(), R.layout.view_float_window, null);
        mRootView.setOnTouchListener(mTouchListener);
    }

    private void savePositionData() {
        SpTools.getInst().saveInt(SpKeyConstant.KEY_DRAG_FLOAT_LOCATION_X, mLayoutParams.x);
        SpTools.getInst().saveInt(SpKeyConstant.KEY_DRAG_FLOAT_LOCATION_Y, mLayoutParams.y);
    }

    private void updateMeasureData() {
        mMinX = 0;
        mMinY = 0;
        mMaxX = screenWidth - mRootView.getWidth();
        mMaxY = screenHeight - mRootView.getHeight();
//        mMinY = ScreenTools.getStatusBarHeight(mContextRef.get());
    }


    private void updateXY(int x, int y) {
        mLayoutParams.x = getValidValue(x, mMinX, mMaxX);
        mLayoutParams.y = getValidValue(y, mMinY, mMaxY);
        mWindowManager.updateViewLayout(mRootView, mLayoutParams);
    }

    private void setParamsType(Context context, WindowManager.LayoutParams params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            req(params);
        } else if (Miui.rom()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                req(params);
            } else {
                params.type = WindowManager.LayoutParams.TYPE_PHONE;
                Miui.req(context, null);
            }
        } else {
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
    }

    private void req(WindowManager.LayoutParams params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
    }

    /*    */
    // 1. onTouch
    private class MyRootTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    updateMeasureData();
                    mRootView.getParent().requestDisallowInterceptTouchEvent(true);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    mHandler.removeCallbacks(mDelayNofityCompress);
                    downTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int newX = (int) event.getRawX();
                    int newY = (int) event.getRawY();
                    int dx = newX - lastX;
                    int dy = newY - lastY;

                    lastX = newX;
                    lastY = newY;
                    updateXY(mLayoutParams.x + dx, mLayoutParams.y + dy);
                    break;
                case MotionEvent.ACTION_UP:
                    if (!isDrag() && mWatcherMap != null && !mWatcherMap.isEmpty()) {
                        notifyGetFocus();
                        mHandler.postDelayed(mDelayAlignBorder, TIME_ALIGN_BORDER_DELAY);
                    }
                    alignBorder();
                    mHandler.postDelayed(mDelayNofityCompress, TIME_LOST_FOCUS_DELAY);
                    break;
            }
            //如果是拖拽则消s耗事件，否则正常传递即可。
            return isDrag() || mRootView.onTouchEvent(event);
        }
    }

    private boolean isDrag() {
        long currentTime = System.currentTimeMillis();
        return currentTime - downTime > TIME_DRAG_MIN;
    }

    // 2. alignBorder
    private void alignBorder() {
        updateMeasureData();
        ObjectAnimator animate;
        int currentX = mLayoutParams.x;
        if (currentX >= mMaxX / 2) {
            //靠右吸附
            animate = ObjectAnimator.ofInt(this, "moveX", currentX, mMaxX);
        } else {
            //靠左吸附
            animate = ObjectAnimator.ofInt(this, "moveX", currentX, mMinX);
        }
        animate.setDuration(500);
        animate.setInterpolator(new BounceInterpolator());
        animate.start();
    }

    // 3. setLayoutParams
    @SuppressLint("ObjectAnimatorBinding")
    private void setMoveX(int x) {
        updateXY(x, mLayoutParams.y);
    }

    private int getValidValue(int i, int minValue, int maxValue) {
        if (minValue > maxValue) {
            return i;
        }
        return i < minValue ? minValue : i > maxValue ? maxValue : i;
    }

    public interface FloatViewWatcher {
        void onGetFocus();
        void onLostFocus();
    }

}
