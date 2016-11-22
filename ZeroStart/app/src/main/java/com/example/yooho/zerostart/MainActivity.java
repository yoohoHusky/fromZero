package com.example.yooho.zerostart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yooho.zerostart.tabhost.IndexEnterActivity;
import com.example.yooho.zerostart.tools.MyUtils;
import com.example.yooho.zerostart.ui.view.listview.MyExpandListActivity;
import com.example.yooho.zerostart.ui.view.hidetitle.HideTitleActivity;
import com.example.yooho.zerostart.ui.view.icon.NumberIconActivity;
import com.example.yooho.zerostart.ui.view.line.DynamicalLineActivity;
import com.example.yooho.zerostart.ui.view.listview.PinnedListActivity;
import com.example.yooho.zerostart.ui.view.listview.PinnedListActivity2;
import com.example.yooho.zerostart.ui.view.weather.WeatherDemoActivity;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String WX_APP_ID = "";
    private IWXAPI wxapi;

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
        }
    }
}
