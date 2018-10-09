package com.example.yooho.husky.myapplication.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by haoou on 2018/9/1.
 */

public class MySetvice extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        stopSelf();
        return null;
    }
}
