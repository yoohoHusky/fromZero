package com.example.yooho.zerostart.system.global.misc;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sdbean.werewolf.global.GlobalSourceProvider;
import com.sdbean.werewolf.global.core.SourceManager;
import com.sdbean.werewolf.global.frame.GlobalBitmapTransformation;
import com.sdbean.werewolf.global.privatebean.GlobalRefreshBean;

import rx.functions.Action1;

public class GlobalSourceShower {


    public static void loadImage(ImageView iv, int resId, boolean refreshRightNow, boolean addRecord) {
        if (iv == null || resId <=0) return;
        loadImage(iv, resId, 0, 0, refreshRightNow, addRecord);
    }

    public static void loadImage(View iv, int resId, boolean refreshRightNow, boolean addRecord) {
        if (iv == null || resId <=0) return;
        DrawableTypeRequest<Integer> request = Glide.with(SourceManager.getInstance().getContext()).load(resId);
        DrawableRequestBuilder<Integer> builder;
        if (addRecord) SourceManager.getInstance().recordView(new GlobalRefreshBean(iv, resId));
        if (addRecord || refreshRightNow) {
            builder = request.skipMemoryCache(true) // 有切换需求的写true，没有切换需求的false；内存缓存清不掉，只能重启app
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE); // 缓存resource，并切换皮肤包的时候清楚缓存
        } else {
            builder = request.skipMemoryCache(false) // 有切换需求的写true，没有切换需求的false；内存缓存清不掉，只能重启app
                    .diskCacheStrategy(DiskCacheStrategy.RESULT); // 缓存resource，并切换皮肤包的时候清楚缓存
        }
        builder.transform(new GlobalBitmapTransformation(SourceManager.getInstance().getContext(), resId))
                .dontAnimate()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        iv.setBackground(resource);
                    }
                });

    }

    public static void loadImage(ImageView iv, int resId, int width, int height, boolean refreshRightNow, boolean addRecord) {
        if (iv == null || resId <=0) return;
        DrawableTypeRequest<Integer> request = Glide.with(SourceManager.getInstance().getContext()).load(resId);
        DrawableRequestBuilder<Integer> builder;
        if (addRecord) SourceManager.getInstance().recordView(new GlobalRefreshBean(iv, resId, width, height));
        if (addRecord || refreshRightNow) {
            builder = request.skipMemoryCache(true) // 有切换需求的写true，没有切换需求的false；内存缓存清不掉，只能重启app
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE); // 缓存resource，并切换皮肤包的时候清楚缓存
        } else {
            builder = request.skipMemoryCache(false) // 有切换需求的写true，没有切换需求的false；内存缓存清不掉，只能重启app
                    .diskCacheStrategy(DiskCacheStrategy.RESULT); // 缓存resource，并切换皮肤包的时候清楚缓存
        }
        if (width != 0 || height != 0) {
            builder = builder.override(width, height);
        }
        builder.dontAnimate().transform(new GlobalBitmapTransformation(SourceManager.getInstance().getContext(), resId)).into(iv);
    }

    public static void loadWebp(SimpleDraweeView draweeView, @IdRes int webpId) {
        if (draweeView == null) return;
        Uri uri = GlobalSourceProvider.getWebpUri(webpId);
        draweeView.setController(Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .setOldController(draweeView.getController())
                .build());
    }

    public static void registerStringObs(Action1<String> action1, int resId) {
        action1.call(SourceManager.getInstance().getSourceProvider().getProxyString(resId));
        SourceManager.getInstance().recordView(new GlobalRefreshBean(action1, resId));
    }
}
