package com.example.yooho.zerostart.net.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.yooho.zerostart.ui.activity.VolleyActivity;

/**
 * Created by haoou on 2018/2/24.
 */

public class VolleyUtils {

    private static VolleyUtils mInst;
    private final RequestQueue mRequestQueue;

    private VolleyUtils(Context applicationContext) {
        mRequestQueue = Volley.newRequestQueue(applicationContext);
    }

    public static VolleyUtils getInst(Context applicationContext) {
        if (mInst == null) {
            synchronized (VolleyUtils.class) {
                if (mInst == null) {
                    mInst = new VolleyUtils(applicationContext);
                }
            }
        }
        return mInst;
    }



}
