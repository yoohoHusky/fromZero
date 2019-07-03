package com.example.yooho.zerostart.system.global.privatebean;

import android.view.View;
import android.widget.ImageView;

import com.example.yooho.zerostart.system.global.core.SourceManager;
import com.example.yooho.zerostart.system.global.misc.GlobalSourceShower;

import rx.functions.Action1;

public class GlobalRefreshBean {
    private static final int REFRESH_BEAN_TYPE_VIEW = 0;
    private static final int REFRESH_BEAN_TYPE_IMAGEVIEW = 1;
    private static final int REFRESH_BEAN_TYPE_STRING_OBSERVER = 2;

    int type;   // 0-image, 1-view
    View view;
    ImageView iv;
    Action1<String> stringAction1;
    int resId;
    int width;
    int height;

    public GlobalRefreshBean(ImageView iv, int resId, int width, int height) {
        this.type = REFRESH_BEAN_TYPE_IMAGEVIEW;
        this.iv = iv;
        this.resId = resId;
        this.width = width;
        this.height = height;
    }

    public GlobalRefreshBean(View view, int resId) {
        this.type = REFRESH_BEAN_TYPE_VIEW;
        this.view = view;
        this.resId = resId;
    }

    public GlobalRefreshBean(Action1<String> action1, int resId) {
        this.type = REFRESH_BEAN_TYPE_STRING_OBSERVER;
        this.stringAction1 = action1;
        this.resId = resId;
    }

    public void refreshSelf() {
        switch (type){
            case REFRESH_BEAN_TYPE_VIEW:
                GlobalSourceShower.loadImage(view, resId, true, false);
                break;
            case REFRESH_BEAN_TYPE_IMAGEVIEW:
                GlobalSourceShower.loadImage(iv, resId, width, height, true, false);
                break;
            case REFRESH_BEAN_TYPE_STRING_OBSERVER:
                stringAction1.call(SourceManager.getInstance().getSourceProvider().getProxyString(resId));
                break;
        }
    }
}
