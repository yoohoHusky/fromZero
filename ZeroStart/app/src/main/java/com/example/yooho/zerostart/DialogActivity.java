package com.example.yooho.zerostart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by yooho on 16/10/12.
 *
 * 重要的点:
 * 1、设置dialog主题
 *   <style name="TransparentDialog" parent="@android:style/Theme.Dialog">
 *   <item name="android:backgroundDimAmount">0.75</item>                    # 设置背景透明度
 *   <item name="android:windowBackground">@android:color/transparent</item> # 设置整个框背景（）
 *   <item name="android:windowFrame">@null</item>                           # 背景是否有边框
 *   <item name="android:windowNoTitle">true</item>                          # 是否有上半部的部分
 *   <item name="android:windowIsFloating">true</item>
 *   <item name="android:windowIsTranslucent">true</item>
 *   <item name="android:background">@android:color/transparent</item>       # 设置title，context背景
 *   <item name="android:backgroundDimEnabled">true</item>
 *   </style>
 * 2、设置dialog动画
 * 1) 得到window
 * 2) 对window设置animation
 * 3) 得到layoutParams
 * 4) 对param设置宽、高、x/y、gravity
 *
 *   Window window = getWindow();
 *   window.setWindowAnimations(R.style.WebViewDialogAnim);                 设置动画
 *   WindowManager.LayoutParams attributes = window.getAttributes();        得到layout参数
 *   attributes.x = x;
 *   attributes.y = y;
 *   attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
 *   attributes.gravity = Gravity.BOTTOM;
 *
 * 3、悬浮按钮实现
 *  1) 通过getService得到WindowManager:(WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
 *  2) 得到WindowManager.LayoutParams
 *  3) 对param设置type:phone                                         (phone则view可点击,overLay不接收点击)
 *  4) 对param设置flags:FLAG_NOT_TOUCH_MODAL|FLAG_NOT_FOCUSABLE       (如果不设置这两个,其他组件将无法获得点击)
 *  5) 设置宽、高、gravity
 *  6) addView(chileView,params)
 *
 *
 */
public class DialogActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        MyOnclickListener myOnclickListener = new MyOnclickListener();
        findViewById(R.id.btn1).setOnClickListener(myOnclickListener);
        findViewById(R.id.btn2).setOnClickListener(myOnclickListener);
        findViewById(R.id.btn3).setOnClickListener(myOnclickListener);
        findViewById(R.id.btn4).setOnClickListener(myOnclickListener);
    }

    class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn1) {
                Dialog dialog = new Dialog(DialogActivity.this, R.style.TransparentDialog);
                View inflate = View.inflate(DialogActivity.this, R.layout.live_download_deleting, null);
                dialog.setContentView(inflate);
                dialog.show();
            } else if (v.getId() == R.id.btn2) {
                MyDialog myDialog = new MyDialog(DialogActivity.this, R.style.TransparentDialog);
                myDialog.showDialog(R.layout.dialog_course_detail_exit, 0, 0);
            } else if (v.getId() == R.id.btn3) {
                MyDialog2 myDialog = new MyDialog2(DialogActivity.this, R.style.TransparentDialog);
                myDialog.setCancelable(true);
                myDialog.showDialog(R.layout.dialog_course_detail_exit, 0, 0);
            } else if (v.getId() == R.id.btn4) {
                View inflate = View.inflate(DialogActivity.this, R.layout.view_float_window, null);
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DialogActivity.this, "click", Toast.LENGTH_SHORT).show();
                    }
                });
                showFloatView(inflate);
            }
        }
    }

    private void showFloatView(View childView) {
        WindowManager wm = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//      params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;   //如果设置为
        params.type = WindowManager.LayoutParams.TYPE_PHONE; //
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        wm.addView(childView, params);
    }


    class MyDialog extends Dialog {
        public MyDialog(Context context) {
            super(context);
        }

        public MyDialog(Context context, int themeResId) {
            super(context, themeResId);
        }

        protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        public void showDialog(int layoutResId, int x, int y) {
            setContentView(layoutResId);
            setShowStyle(x, y);
            setCancelable(true);
            show();
        }

        private void setShowStyle(int x, int y) {
            Window window = getWindow();
            window.setWindowAnimations(R.style.WebViewDialogAnim);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.x = x;
            attributes.y = y;
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.gravity = Gravity.BOTTOM;

        }
    }

    class MyDialog2 extends Dialog {
        public MyDialog2(Context context) {
            super(context);
        }

        public MyDialog2(Context context, int themeResId) {
            super(context, themeResId);
        }

        protected MyDialog2(Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        public void showDialog(int layoutResId, int x, int y) {
            setContentView(layoutResId);
            setShowStyle(x, y);
            setCancelable(true);
            show();
        }

        private void setShowStyle(int x, int y) {
            Window window = getWindow();
            window.setWindowAnimations(R.style.WebViewDialogAnim2);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.x = x;
            attributes.y = y;
//            attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.gravity = Gravity.LEFT;
        }
    }
}
