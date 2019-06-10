package com.example.yooho.zerostart.system.global.frame;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.sdbean.werewolf.global.GlobalSourceProvider;
import com.sdbean.werewolf.global.core.SourceManager;

public class GlobalBitmapTransformation extends BitmapTransformation {
    private final int mResId;

    public GlobalBitmapTransformation(Context context, int resId) {
        super(context);
        mResId = resId;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (!SourceManager.getInstance().isUseSkin()) {
            return toTransform;
        }
        if (GlobalSourceProvider.getBitmap(mResId, outWidth, outHeight) == null) {
            return toTransform;
        }
        return GlobalSourceProvider.getBitmap(mResId, outWidth, outHeight);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
