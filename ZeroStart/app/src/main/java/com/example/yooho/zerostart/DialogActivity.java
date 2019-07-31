package com.example.yooho.zerostart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yooho.zerostart.ui.activity.AddChooseViewActivity;

import java.util.TreeMap;

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
 * 4、动画设置interpolator篡改器,调节动画播放速度
 *   AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
 *   AccelerateInterpolator  在动画开始的地方速率改变比较慢，然后开始加速
 *   AnticipateInterpolator 开始的时候向后然后向前甩
 *   AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
 *   BounceInterpolator   动画结束的时候弹起
 *   CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
 *   DecelerateInterpolator 在动画开始的地方快然后慢
 *   LinearInterpolator   以常量速率改变
 *   OvershootInterpolator    向前甩一定值后再回到原来位置
 *
 */
public class DialogActivity extends Activity {

    private RelativeLayout viewRoot;
    private PopupWindow popupWindow;
    String str = "";
    private Dialog mRankDialog;
    private ListView mDialogPullList;
    private RelativeLayout mDialogMineContianer;
    private ImageView mDialogAvatar;
    private TextView mDialogRankValue;
    private TextView mDialogScoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        // 测试edit与toast的影响
        initWindow();
        viewRoot = (RelativeLayout) findViewById(R.id.dialog_root);
        String[] ls = str.split("l");
        String l = ls[ls.length - 1];

        MyOnclickListener myOnclickListener = new MyOnclickListener();
        findViewById(R.id.btn1).setOnClickListener(myOnclickListener);
        findViewById(R.id.btn2).setOnClickListener(myOnclickListener);
        findViewById(R.id.btn3).setOnClickListener(myOnclickListener);
        findViewById(R.id.btn4).setOnClickListener(myOnclickListener);
        findViewById(R.id.btn5).setOnClickListener(myOnclickListener);
        findViewById(R.id.btn6).setOnClickListener(myOnclickListener);
        findViewById(R.id.btn7).setOnClickListener(myOnclickListener);
        ((EditText)findViewById(R.id.btn8)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ToastStr = "<font color='#2319DC'>"+ s + "  再按一次退出浮生绘"+"</font>";
                Toast toast = Toast.makeText(DialogActivity.this, Html.fromHtml(ToastStr) , Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TreeMap<String, String> msp = new TreeMap<>();
        String lastTitle = "aaaa";
        msp.put("1", lastTitle);
        lastTitle = "bbbbb";
        msp.put("2", lastTitle);
        Log.e("SS", msp.toString());
        Log.e("SS", msp.containsKey("1") + "");
        Log.e("SS", msp.containsKey("") + "");
        String[] as = "".split("a");
        Log.e("SS", as.length + "   " + as.toString());

    }

    private void initWindow() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
//                mRankDialog.show();
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
            } else if (v.getId() == R.id.btn5) {
                if (popupWindow == null) {
                    View inflate = View.inflate(DialogActivity.this, R.layout.live_my_course_save_guide, null);
                    popupWindow = new PopupWindow(inflate, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    popupWindow.setAnimationStyle(R.style.live_my_course_save_guide);
                }
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAtLocation(viewRoot, (Gravity.LEFT | Gravity.TOP), 100, 300);
                }
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            } else if (v.getId() == R.id.btn6) {
                Intent intent = new Intent(DialogActivity.this, AddChooseViewActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.btn7) {
                MyDialog3 myDialog = new MyDialog3(DialogActivity.this, R.style.TransparentDialog);
                myDialog.setCancelable(true);
                myDialog.showDialog(R.layout.dialog_course_detail_splash, 0, 0);
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)    {
        char keyValue = (char) event.getUnicodeChar();
        System.out.println("我点了---->>>>"+keyValue);
        return super.onKeyDown(keyCode, event);
    }

    private void showFloatView(View childView) {
        WindowManager wm = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        LayoutParams.TYPE_TOAST or TYPE_APPLICATION_PANEL
//      params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;   //如果设置为
        params.type = WindowManager.LayoutParams.TYPE_TOAST; //
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
//            window.setWindowAnimations(R.style.live_my_course_save_guide);
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

    class MyDialog3 extends Dialog {
        public MyDialog3(Context context) {
            super(context);
        }

        public MyDialog3(Context context, int themeResId) {
            super(context, themeResId);
        }

        protected MyDialog3(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
            window.setWindowAnimations(R.style.WebViewDialogAnim3);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.x = 0;
            attributes.y = 0;
//            attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.gravity = Gravity.LEFT;
        }
    }

}
