package com.example.yooho.zerostart.ui.toast;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 2017/2/14.
 */
public class ToastActivity extends FragmentActivity {
    private Toast colorToast;

    private static final int CHANGE_COLOR_TOAST_STR = 1001;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_COLOR_TOAST_STR:
                    if (msg.obj instanceof String || msg.obj instanceof CharSequence) {
                        colorToast.setText((CharSequence) msg.obj);
                    }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);


        ToastClickListener toastClickListener = new ToastClickListener();
        findViewById(R.id.toast_btn1).setOnClickListener(toastClickListener);
        findViewById(R.id.toast_btn2).setOnClickListener(toastClickListener);
        findViewById(R.id.toast_btn3).setOnClickListener(toastClickListener);


    }

    class ToastClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.toast_btn1:
                    showColorToast(ToastActivity.this, "之前", " +5 ", "之后");
                    break;
                case R.id.toast_btn2:
                    callPermissionToast();
                    break;
                case R.id.toast_btn3:
                    callNoPermissionToast();
                    break;
            }
        }


    }

    private void callNoPermissionToast() {
        FlexibleAssembledToast.getInstance(this).showToastWithoutPermission("没有权限的toast", "+5", "之后");
    }

    private void callPermissionToast() {
        FlexibleAssembledToast.getInstance(this).showToastWithPermission("get权限的toast", "+5", "之后");
    }

    private void showColorToast(Context context, String preStr, String colorStr, String sufStr) {

        String ToastStr = preStr + "<font size='15' color='#fb7f2d'>" + colorStr + "</font>" + sufStr;
        colorToast = Toast.makeText(context, Html.fromHtml(ToastStr) , Toast.LENGTH_SHORT);
        colorToast.show();

        String spaceStr = "";
        for (int index=0; index< colorStr.length(); index++) {
            spaceStr += "  ";
        }

        Message message = mHandler.obtainMessage();
        message.obj = preStr + spaceStr + sufStr;
        message.what = CHANGE_COLOR_TOAST_STR;
        mHandler.sendMessageDelayed(message, 500);
    }
}
