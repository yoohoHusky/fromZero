package com.example.yooho.zerostart.ui.toast;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yooho.zerostart.R;

import java.lang.ref.WeakReference;

/**
 * Created by yooho on 2017/2/14.
 */
public class FlexibleAssembledToast {

    private static FlexibleAssembledToast mInst;

    private View toastView;
    private WindowManager wm;
    private WindowManager.LayoutParams params;
    AnimationSet mTranslateAnimation;
    WeakReference<Context> mContextRef;

    private TextView mPreTv;
    private TextView mSufTv;
    private TextView mColorTv;
    private TextView mColorTvCopy;
    private TextView mColorTvNoPermission;
    private ImageView mBackgroundIv;

    private final int CODE_START_MOVE_COLOR_VIEW_WITH_PERMISSION = 1001;
    private final int CODE_START_MOVE_COLOR_VIEW_NO_PERMISSION = 1002;
    private final int CODE_CLOSE_COLOR_VIEW = 1003;

    private long COLOR_VIEW_STATIC_SHOW_LONG = 2500L;    // colorView静止显示的时长,该时长之后开始做移动
    private long COLOR_VIEW_MOVE_DURATION = 2000L;      // colorView移动动画的时长

    private boolean mIsShowing = false;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_START_MOVE_COLOR_VIEW_WITH_PERMISSION:
                    moveColorView(true);
                    break;
                case CODE_START_MOVE_COLOR_VIEW_NO_PERMISSION:
                    moveColorView(false);
                    break;
                case CODE_CLOSE_COLOR_VIEW:
                    closeToast();
                    break;
            }
        }
    };

    public static FlexibleAssembledToast getInstance(Context context) {
        if (mInst == null) {
            synchronized (FlexibleAssembledToast.class) {
                if (mInst == null) {
                    mInst = new FlexibleAssembledToast(context);
                }
            }
        }
        return mInst;
    }

    private FlexibleAssembledToast(Context context) {
        mContextRef = new WeakReference<>(context);
        initView();
    }

    private void initView() {
        initToastView();
        initWindowManager();
        initTranslateAnimation();
    }

    private void initTranslateAnimation() {
        mTranslateAnimation = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f);

        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, -0.5f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, -0.7f);
        mTranslateAnimation.addAnimation(scaleAnimation);
        mTranslateAnimation.addAnimation(translateAnimation);


        mTranslateAnimation.setDuration(COLOR_VIEW_MOVE_DURATION);
        mTranslateAnimation.setInterpolator(new DecelerateInterpolator());
        mTranslateAnimation.setFillAfter(true);

    }

    private void initWindowManager() {
        if (mContextRef.get() == null) {
            return;
        }
        wm = (WindowManager)mContextRef.get().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.x = 0;
        params.y = 0;
    }

    private void initToastView() {
        if (mContextRef.get() == null) {
            return;
        }
        toastView = View.inflate(mContextRef.get(), R.layout.live_view_toast_move, null);
        mPreTv = (TextView) toastView.findViewById(R.id.toast_pre_tv);
        mColorTv = (TextView) toastView.findViewById(R.id.toast_color_tv);
        mColorTvCopy = (TextView) toastView.findViewById(R.id.toast_color_tv_copy);
        mColorTvNoPermission = (TextView) toastView.findViewById(R.id.toast_color_tv_no_permission);
        mSufTv = (TextView) toastView.findViewById(R.id.toast_suf_tv);
        mBackgroundIv = (ImageView) toastView.findViewById(R.id.toast_bg_iv);
    }

    private CharSequence getHtmlStr(String preStr, String colorStr, String sufStr) {
        if (mContextRef.get() == null) {
            return "";
        }
        String ToastStr = preStr + "<font size='6' color='#fb7f2d'>" + colorStr + "</font>" + sufStr;
        return Html.fromHtml(ToastStr);
    }

    private void handleOtherView(boolean hasPermisson) {
        mPreTv.setVisibility(hasPermisson? View.GONE: View.VISIBLE);
        mColorTv.setVisibility(hasPermisson? View.GONE: View.VISIBLE);
        mColorTvCopy.setVisibility(hasPermisson? View.GONE: View.VISIBLE);
        mSufTv.setVisibility(hasPermisson? View.GONE: View.VISIBLE);
        mBackgroundIv.setVisibility(hasPermisson? View.GONE: View.VISIBLE);

        mColorTvNoPermission.setVisibility(hasPermisson ? View.VISIBLE : View.GONE);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void closeToast() {
        mIsShowing = false;
        if (wm == null || toastView == null) {
            return;
        }
        if (toastView.isAttachedToWindow()) {
            wm.removeView(toastView);
        }
    }

    private void moveColorView(boolean hasPermission) {
        if (hasPermission) {
            mColorTv.setVisibility(View.INVISIBLE);
            mColorTvCopy.startAnimation(mTranslateAnimation);
        } else {
            wm.addView(toastView, params);
            mColorTvNoPermission.startAnimation(mTranslateAnimation);
        }
        mHandler.sendEmptyMessageDelayed(CODE_CLOSE_COLOR_VIEW, COLOR_VIEW_MOVE_DURATION);
    }

    public void showToastWithPermission(String preStr, String colorStr, String sufStr) {
        if (mIsShowing) {
            return;
        }
        handleOtherView(false);

        mPreTv.setText(preStr);
        mColorTv.setText(colorStr);
        mColorTvCopy.setText(colorStr);
        mSufTv.setText(sufStr);

        wm.addView(toastView, params);
        mHandler.sendEmptyMessageDelayed(CODE_START_MOVE_COLOR_VIEW_WITH_PERMISSION, COLOR_VIEW_STATIC_SHOW_LONG);
    }

    public void showToastWithoutPermission(String preStr, String colorStr, String sufStr) {
        if (mContextRef.get() == null) {
            return;
        }
        if (mIsShowing) {
            return;
        }
        handleOtherView(true);
        CharSequence htmlStr = getHtmlStr(preStr, colorStr, sufStr);
        Toast.makeText(mContextRef.get(), htmlStr, Toast.LENGTH_SHORT).show();
        mColorTvNoPermission.setText(colorStr);
        mHandler.sendEmptyMessageDelayed(CODE_START_MOVE_COLOR_VIEW_NO_PERMISSION, COLOR_VIEW_STATIC_SHOW_LONG);
    }

}
