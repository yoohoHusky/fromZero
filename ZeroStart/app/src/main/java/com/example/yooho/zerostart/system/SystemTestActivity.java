package com.example.yooho.zerostart.system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.yooho.zerostart.R;

import java.lang.reflect.Field;

/**
 * Created by yooho on 2017/3/6.
 */
public class SystemTestActivity extends FragmentActivity {
    private static boolean showLight = true;
    private View inflate;
    private View inflate2;
    private boolean fullScreen;
    private SystemListener systemListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_test);


        systemListener = new SystemListener();
        findViewById(R.id.system_context_add_view).setOnClickListener(systemListener);
        findViewById(R.id.system_context_add_view_safe).setOnClickListener(systemListener);
        findViewById(R.id.system_control_status_bar).setOnClickListener(systemListener);

    }

    class SystemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.system_context_add_view:
                    if (inflate == null) {
                        inflate = View.inflate(SystemTestActivity.this, R.layout.relative_cover_test, null);
                        inflate.findViewById(R.id.cover_test_tv).setOnClickListener(systemListener);
                        inflate.findViewById(R.id.cover_test_btn1).setOnClickListener(systemListener);
                        inflate.findViewById(R.id.cover_test_btn2).setOnClickListener(systemListener);
                        inflate.findViewById(R.id.cover_test_btn3).setOnClickListener(systemListener);
                    }
                    ((ViewGroup)findViewById(android.R.id.content)).addView(inflate);
                    break;
                case R.id.system_context_add_view_safe:
                    if (inflate2 == null) {
                        inflate2 = View.inflate(SystemTestActivity.this, R.layout.relative_cover_test2, null);
                    }
                    ((ViewGroup)findViewById(android.R.id.content)).removeView(inflate2);
                    ((ViewGroup)findViewById(android.R.id.content)).addView(inflate2);
                    break;
                case R.id.system_control_status_bar:
                    fullScreen = !fullScreen;
                    showButtons(!fullScreen);
                    showSystemUi(!fullScreen);
                    setFullScreen(fullScreen);
                    break;
                case R.id.cover_test_tv:
                    Log.e("SS", "cover_test_tv");
                    break;
                case R.id.cover_test_btn1:
                    Log.e("SS", "cover_test_btn1");
                    break;
                case R.id.cover_test_btn2:
                    Log.e("SS", "cover_test_btn2");
                    break;
                case R.id.cover_test_btn3:
                    Log.e("SS", "cover_test_btn3");
                    break;
            }
        }
    }


    /**
     * 控制屏幕亮度,但是这个好像没生效
     * @param showButtons
     */
    void showButtons(boolean showButtons) {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        float val = showButtons ? -1 : 0;
        try {
            Field buttonBrightness = layoutParams.getClass().getField("buttonBrightness");
            buttonBrightness.set(layoutParams, val);
        } catch (Exception e) {
            e.printStackTrace();
        }
        window.setAttributes(layoutParams);
    }

    /**
     * 控制下方虚拟反回建是否出现
     * @param visible
     */
    private void showSystemUi(boolean visible) {
        int flag = visible ? 0 : View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LOW_PROFILE;
        findViewById(android.R.id.content).setSystemUiVisibility(flag);
    }

    /**
     * 控制上方statusBar是否出现
     * @param enable
     */
    public void setFullScreen(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            //activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            //activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
