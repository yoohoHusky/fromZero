package com.example.yooho.zerostart;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.yooho.zerostart.SVG.SVGControlActivity;
import com.example.yooho.zerostart.black.activity_factory.ActivityFactoryAct;
import com.example.yooho.zerostart.black.theme_factory.ThemeFactoryActivity;
import com.example.yooho.zerostart.databing.DatabingActivity;
import com.example.yooho.zerostart.image.ImageActivity;
import com.example.yooho.zerostart.jnicode.JniProxy;
import com.example.yooho.zerostart.mvvm.activity.MVVMActivity;
import com.example.yooho.zerostart.net.okhttp.OkhttpActivity;
import com.example.yooho.zerostart.net.retrofit.RetrofitActivity;
import com.example.yooho.zerostart.net.rxjava.RxJavaDemoActivity;
import com.example.yooho.zerostart.screenshotter.ScreenShotterAct;
import com.example.yooho.zerostart.system.SystemTestActivity;
import com.example.yooho.zerostart.tabhost.IndexEnterActivity;
import com.example.yooho.zerostart.tools.ExeCommand;
import com.example.yooho.zerostart.tools.MiscTools;
import com.example.yooho.zerostart.tools.MyUtils;
import com.example.yooho.zerostart.tools.UsbDeviceHelper;
import com.example.yooho.zerostart.ui.VerticalSeekbarActivity;
import com.example.yooho.zerostart.ui.activity.AnimateListActivity;
import com.example.yooho.zerostart.ui.activity.PageUiActivity;
import com.example.yooho.zerostart.ui.activity.RecycleViewActivity;
import com.example.yooho.zerostart.ui.activity.VolleyActivity;
import com.example.yooho.zerostart.ui.theme.ThemeActivity;
import com.example.yooho.zerostart.ui.theme.ThemeActivity2;
import com.example.yooho.zerostart.ui.toast.ToastActivity;
import com.example.yooho.zerostart.ui.view.bitmap.blur.BlurMaskActivity;
import com.example.yooho.zerostart.ui.view.bitmap.splite.SplitBitmapActivity;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.usage.UsageStatsManager.INTERVAL_BEST;
import static android.app.usage.UsageStatsManager.INTERVAL_DAILY;
import static android.app.usage.UsageStatsManager.INTERVAL_MONTHLY;
import static android.app.usage.UsageStatsManager.INTERVAL_YEARLY;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String WX_APP_ID = "";
    private IWXAPI wxapi;
    private View inflate;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        findViewById(R.id.model_image).setOnClickListener(this);
        findViewById(R.id.model_data_Binding).setOnClickListener(this);
        findViewById(R.id.model_svg_seek).setOnClickListener(this);
        findViewById(R.id.model_blur_mask).setOnClickListener(this);
        findViewById(R.id.model_rxjava).setOnClickListener(this);
        findViewById(R.id.model_split_bitmap).setOnClickListener(this);
        findViewById(R.id.model_activity_factory).setOnClickListener(this);
        findViewById(R.id.model_activity_theme).setOnClickListener(this);
        findViewById(R.id.model_activity_theme2).setOnClickListener(this);
        findViewById(R.id.model_activity_theme_factory).setOnClickListener(this);
        findViewById(R.id.model_activity_retrofit).setOnClickListener(this);
        findViewById(R.id.model_activity_animate_list).setOnClickListener(this);
        findViewById(R.id.model_activity_ui).setOnClickListener(this);

        MyUtils.init(this);

        initWeChat();

        MiscTools.getPackagesForUid(this, -1);
        MiscTools.getPackagesForUid(this, 10208);

        String str = new ExeCommand().run("dumpsys netstats detail", 10000).getResult();
        Log.e("SS", str);
    }


    BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "SS", Toast.LENGTH_LONG).show();
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                Log.e("SS", "拔出usb了");
                UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                    Toast.makeText(context, "设备的ProductId值为：" + device.getProductId() + "     设备的VendorId值为：" + device.getVendorId(),
                            Toast.LENGTH_LONG).show();
                    Log.e("SS", "设备的ProductId值为：" + device.getProductId());
                    Log.e("SS", "设备的VendorId值为：" + device.getVendorId());
                }
            } else if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                Log.e("SS", "插入usb了");
            }
        }
    };




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
        } else if (v.getId() == R.id.model_image) {
            startActivity(new Intent(this, ImageActivity.class));
        } else if (v.getId() == R.id.model_data_Binding) {
            startActivity(new Intent(this, DatabingActivity.class));
        } else if (v.getId() == R.id.model_svg_seek) {
            startActivity(new Intent(this, SVGControlActivity.class));
        } else if (v.getId() == R.id.model_blur_mask) {
            startActivity(new Intent(this, BlurMaskActivity.class));
        } else if (v.getId() == R.id.model_rxjava) {
            startActivity(new Intent(this, RxJavaDemoActivity.class));
        } else if (v.getId() == R.id.model_split_bitmap) {
            startActivity(new Intent(this, SplitBitmapActivity.class));
        } else if (v.getId() == R.id.model_activity_factory) {
            startActivity(new Intent(this, ActivityFactoryAct.class));
        } else if (v.getId() == R.id.model_activity_theme) {
            startActivity(new Intent(this, ThemeActivity.class));
        } else if (v.getId() == R.id.model_activity_theme2) {
            startActivity(new Intent(this, ThemeActivity2.class));
        } else if (v.getId() == R.id.model_activity_theme_factory) {
            startActivity(new Intent(this, ThemeFactoryActivity.class));
        } else if (v.getId() == R.id.model_activity_retrofit) {
            startActivity(new Intent(this, RetrofitActivity.class));
        } else if (v.getId() == R.id.model_activity_animate_list) {
            startActivity(new Intent(this, AnimateListActivity.class));
        } else if (v.getId() == R.id.model_activity_ui) {
            startActivity(new Intent(this, PageUiActivity.class));
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }


    }
}
