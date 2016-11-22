package com.example.yooho.zerostart.tools;

import android.content.Context;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by yooho on 2016/11/22.
 */
public class MyUtils {

    private static WeakReference<Context> mContextRef;

    public static void init(Context context) {
        mContextRef = new WeakReference<Context>(context);
    }


    public static void showToast(String toast) {
        Toast.makeText(mContextRef.get(), toast, Toast.LENGTH_SHORT).show();
    }

}
