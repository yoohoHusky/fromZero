package com.example.yooho.zerostart.system.global.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static com.example.yooho.zerostart.system.global.misc.SkinConstant.GLOBAL_SP_NAME_SPACE;

public class SkinSpHelper {

    private static SkinSpHelper mInst;
    private static SharedPreferences mSharedPre;


    private SkinSpHelper() {}

    public void init(Context context) {
        mSharedPre = context.getSharedPreferences(GLOBAL_SP_NAME_SPACE, Activity.MODE_PRIVATE);
    }

    public static SkinSpHelper getInst() {
        if (mInst == null) {
            synchronized (SkinSpHelper.class) {
                if (mInst == null) {
                    mInst = new SkinSpHelper();
                }
            }
        }
        return mInst;
    }

    public long getLongValue(String key, long defValue) {
        return mSharedPre.getLong(key, defValue);
    }

    public void putLongValue(String key, long value) {
        SharedPreferences.Editor edit = mSharedPre.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public String getStringValue(String key, String defValue) {
        return mSharedPre.getString(key, defValue);
    }

    public void putStringValue(String key, String value) {
        SharedPreferences.Editor edit = mSharedPre.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public void releaseSelf() {
        mSharedPre = null;
    }
}
