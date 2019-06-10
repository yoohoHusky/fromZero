package com.example.yooho.zerostart.system.global;


import android.graphics.Bitmap;
import android.net.Uri;

import com.sdbean.werewolf.global.core.SourceManager;

public class GlobalSourceProvider {

    /**
     * 根据R.drawable.xxx 得到指定图片
     */
    public static Bitmap getBitmap(int resId) {
        return SourceManager.getInstance().getSourceProvider().getProxyDrawable(resId);
    }

    /**
     * 根据R.drawable.xxx 得到指定大小的图片
     */
    public static Bitmap getBitmap(int resId, int width, int height) {
        return SourceManager.getInstance().getSourceProvider().getProxyDrawable(resId, width, height);
    }

    /**
     * 根据R.string.xxx 得到指文案
     */
    public static String getString(int resId) {
        return SourceManager.getInstance().getSourceProvider().getProxyString(resId);
    }

    /**
     * 根据R.drawable.xxx（webp资源） 得到指定的webp对应的uri，配合Fresco可直接使用
     */
    public static Uri getWebpUri(int webpId) {
        return SourceManager.getInstance().getSourceProvider().getWebpUri(webpId);
    }

    /**
     * 根据R.raw.xxx（sound资源） 得到指定的sound文件对应的uri，配合MediaPlayer可直接使用
     */
    public static Uri getSoundUri(int soundId) {
        return SourceManager.getInstance().getSourceProvider().getSoundUri(soundId);
    }
}