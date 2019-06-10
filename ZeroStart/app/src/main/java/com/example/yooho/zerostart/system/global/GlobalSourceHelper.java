package com.example.yooho.zerostart.system.global;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sdbean.werewolf.global.misc.GlobalSourceShower;

import rx.functions.Action1;

/**
 * 代理了图片的获取、缓存、展示
 * 如果需要sdk处理图片的缓存、销毁等事情，选用GlobalImageHelper类
 * 如果不需要sdk管理图片的缓存问题，选用GlobalSourceProvider类
 */
public class GlobalSourceHelper {

    /**
     * 为src设置图片
     */
    public static void loadImage(ImageView iv, int resId, boolean needRecord) {
        GlobalSourceShower.loadImage(iv, resId, needRecord, needRecord);
    }

    /**
     * 为background设置图片
     */
    public static void loadImage(View iv, int resId, boolean needRecord) {
        GlobalSourceShower.loadImage(iv, resId, needRecord, needRecord);
    }

    /**
     * 为src设置指定了大小的图片
     */
    public static void loadImage(ImageView iv, int resId, int width, int height, boolean needRecord) {
        GlobalSourceShower.loadImage(iv, resId, width, height, needRecord, needRecord);
    }

    /**
     * 为view设置string，并在切换资源时，自动调用更新
     */
    public static void registerTextObserver(int resId, Action1<String> action1) {
        GlobalSourceShower.registerStringObs(action1, resId);
    }

    /**
     * 为DraweeView设置webp资源
     */
    public static void loadWebp(SimpleDraweeView draweeView, @IdRes int webpId) {
        GlobalSourceShower.loadWebp(draweeView, webpId);
    }

    public static void loadImage(ImageView iv, int resId) {
        loadImage(iv, resId, false);
    }

    public static void loadImage(ImageView iv, int resId, int width, int height) {
        loadImage(iv, resId, width, height, false);
    }

    public static void loadImage(View iv, int resId) {
        loadImage(iv, resId, false);
    }
}
