package com.example.yooho.zerostart.tools;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.nfc.Tag;
import android.text.TextUtils;
import android.widget.Toast;

public class UsbDeviceHelper extends BroadcastReceiver {
    //android标准的广播action
    private static final String ACTION_USB_DEVICE_PERMISSION = "com.android.example.USB_PERMISSION";
    private Context context;
    private UsbManager usbManager;

    private static UsbDeviceHelper mInst;
    private UsbDeviceHelper() {}
    public static UsbDeviceHelper getInst() {
        if (mInst == null) {
            synchronized (UsbDeviceHelper.class) {
                if (mInst == null) {
                    mInst = new UsbDeviceHelper();
                }
            }
        }
        return mInst;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        Toast.makeText(context, "sssssss", Toast.LENGTH_LONG).show();
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) return;
        if (action.equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)){
            //设备插入，获取这个插入的UsbDevice
            UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            //向用户获取连接USB设备的授权
            requestUserPermission(usbDevice);
        }else if (action.equals(UsbManager.ACTION_USB_DEVICE_DETACHED)){
            //设备拔下，资源释放
        }else if (action.equals(ACTION_USB_DEVICE_PERMISSION)){
            //获取连接设备的权限
            boolean isGranted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED,false);
            if (isGranted){
                //用户已授权
            }else {
                //用户未授权
            }
        }
    }

    /**
     * 检测到设备插入之后，向用户请求连接设备的权限
     */
    private void requestUserPermission(UsbDevice usbDevice) {
        if (usbManager.hasPermission(usbDevice)){
            //已经有权限的话，直接初始化驱动
            //判断已经授予权限
            return;
        }
        //发一个延时广播
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(context, 0,
                new Intent(ACTION_USB_DEVICE_PERMISSION), 0);
        //这一句会由系统弹出对话框，向用户获取连接授权
        usbManager.requestPermission(usbDevice, mPendingIntent);
    }

    /**
     * 注册USB设备插拔事件监听
     */
    public void registerUsbEventReceiver(Context context){
        this.context = context.getApplicationContext();
        usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_DEVICE_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        this.context.registerReceiver(this, filter);
    }
}