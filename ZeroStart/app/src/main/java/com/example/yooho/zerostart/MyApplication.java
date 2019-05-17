package com.example.yooho.zerostart;

import android.app.Application;

import com.example.yooho.zerostart.black.theme_factory.SkinManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
    }
}
