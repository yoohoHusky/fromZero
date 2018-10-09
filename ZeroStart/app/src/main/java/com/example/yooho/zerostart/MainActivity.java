package com.example.yooho.zerostart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.yooho.zerostart.jnicode.JniProxy;
import com.example.yooho.zerostart.mvvm.activity.MVVMActivity;
import com.example.yooho.zerostart.net.okhttp.OkhttpActivity;
import com.example.yooho.zerostart.screenshotter.ScreenShotterAct;
import com.example.yooho.zerostart.system.SystemTestActivity;
import com.example.yooho.zerostart.tabhost.IndexEnterActivity;
import com.example.yooho.zerostart.tools.MyUtils;
import com.example.yooho.zerostart.ui.VerticalSeekbarActivity;
import com.example.yooho.zerostart.ui.activity.RecycleViewActivity;
import com.example.yooho.zerostart.ui.activity.VolleyActivity;
import com.example.yooho.zerostart.ui.toast.ToastActivity;
import com.example.yooho.zerostart.ui.view.hidetitle.HideTitleActivity;
import com.example.yooho.zerostart.ui.view.icon.NumberIconActivity;
import com.example.yooho.zerostart.ui.view.line.DynamicalLineActivity;
import com.example.yooho.zerostart.ui.view.listview.MyExpandListActivity;
import com.example.yooho.zerostart.ui.view.listview.PinnedListActivity;
import com.example.yooho.zerostart.ui.view.listview.PinnedListActivity2;
import com.example.yooho.zerostart.ui.view.processbar.DashBoardActivity;
import com.example.yooho.zerostart.ui.view.weather.WeatherDemoActivity;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String WX_APP_ID = "";
    private IWXAPI wxapi;
    private View inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.model_download).setOnClickListener(this);
        findViewById(R.id.model_dialog).setOnClickListener(this);
        findViewById(R.id.model_text_view).setOnClickListener(this);
        findViewById(R.id.model_tab_host).setOnClickListener(this);
        findViewById(R.id.model_send_wx).setOnClickListener(this);
        findViewById(R.id.model_num_icon).setOnClickListener(this);
        findViewById(R.id.model_dynamical_line).setOnClickListener(this);
        findViewById(R.id.model_weather).setOnClickListener(this);
        findViewById(R.id.model_hide_title).setOnClickListener(this);
        findViewById(R.id.model_expand_list).setOnClickListener(this);
        findViewById(R.id.model_pinned_list).setOnClickListener(this);
        findViewById(R.id.model_pinned_list2).setOnClickListener(this);
        findViewById(R.id.model_dashboard).setOnClickListener(this);
        findViewById(R.id.screen_shot).setOnClickListener(this);
        findViewById(R.id.model_toast).setOnClickListener(this);
        findViewById(R.id.model_seek_bar).setOnClickListener(this);
        findViewById(R.id.model_system_tools).setOnClickListener(this);
        findViewById(R.id.model_mvvm).setOnClickListener(this);
        findViewById(R.id.model_recycler_view).setOnClickListener(this);
        findViewById(R.id.model_volley).setOnClickListener(this);
        findViewById(R.id.model_okhttp).setOnClickListener(this);
        findViewById(R.id.model_jni).setOnClickListener(this);

        MyUtils.init(this);

        initWeChat();
    }

    private void initWeChat() {
        wxapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        wxapi.registerApp(WX_APP_ID);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.model_download) {
            startActivity(new Intent(this, DownloadModelActivity.class));
        } else if (v.getId() == R.id.model_dialog) {
            startActivity(new Intent(this, DialogActivity.class));
        } else if (v.getId() == R.id.model_text_view) {
            startActivity(new Intent(this, TextViewActivity.class));
        } else if (v.getId() == R.id.model_tab_host) {
            startActivity(new Intent(this, IndexEnterActivity.class));
        } else if (v.getId() == R.id.model_send_wx) {
            WXTextObject wxTextObject = new WXTextObject();
            wxTextObject.text = "text";

            WXMediaMessage wxMediaMessage = new WXMediaMessage();
            wxMediaMessage.mediaObject = wxTextObject;
            wxMediaMessage.description = "description";

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = wxMediaMessage;
            wxapi.sendReq(req);
        } else if (v.getId() == R.id.model_num_icon) {
            startActivity(new Intent(this, NumberIconActivity.class));
        } else if (v.getId() == R.id.model_dynamical_line) {
            startActivity(new Intent(this, DynamicalLineActivity.class));
        } else if (v.getId() == R.id.model_weather) {
            startActivity(new Intent(this, WeatherDemoActivity.class));
        } else if (v.getId() == R.id.model_hide_title) {
            startActivity(new Intent(this, HideTitleActivity.class));
        } else if (v.getId() == R.id.model_expand_list) {
            startActivity(new Intent(this, MyExpandListActivity.class));
        } else if (v.getId() == R.id.model_pinned_list) {
            startActivity(new Intent(this, PinnedListActivity.class));
        } else if (v.getId() == R.id.model_pinned_list2) {
            startActivity(new Intent(this, PinnedListActivity2.class));
        } else if (v.getId() == R.id.model_dashboard) {
            startActivity(new Intent(this, DashBoardActivity.class));
        } else if (v.getId() == R.id.screen_shot) {
            startActivity(new Intent(this, ScreenShotterAct.class));
        } else if (v.getId() == R.id.model_toast) {
            startActivity(new Intent(this, ToastActivity.class));
        } else if (v.getId() == R.id.model_seek_bar) {
            startActivity(new Intent(this, VerticalSeekbarActivity.class));
        } else if (v.getId() == R.id.model_system_tools) {
            startActivity(new Intent(this, SystemTestActivity.class));
        } else if (v.getId() == R.id.model_mvvm) {
            startActivity(new Intent(this, MVVMActivity.class));
        } else if (v.getId() == R.id.model_recycler_view) {
            startActivity(new Intent(this, RecycleViewActivity.class));
        } else if (v.getId() == R.id.model_volley) {
            startActivity(new Intent(this, VolleyActivity.class));
        } else if (v.getId() == R.id.model_okhttp) {
            startActivity(new Intent(this, OkhttpActivity.class));
        } else if (v.getId() == R.id.model_jni) {
            String result = JniProxy.getNativeString("输入内容");
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
