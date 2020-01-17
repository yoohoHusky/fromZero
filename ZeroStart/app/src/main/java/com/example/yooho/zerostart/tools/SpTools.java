package com.example.yooho.zerostart.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SpTools {
    private static final String FILE_NAME = "zmt_misc_sp";

    private static SpTools mInst;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEdit;

    private SpTools() {}

    public static SpTools getInst() {
        if (mInst == null) {
            synchronized (SpTools.class) {
                if (mInst == null) {
                    mInst = new SpTools();
                }
            }
        }
        return mInst;
    }

    public void init(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mEdit = mSharedPreferences.edit();
    }

    public String getString(String key) {
        if (mSharedPreferences == null) return "";
        return mSharedPreferences.getString(key, "");
    }

    public void saveString(String key, String value) {
        if (mSharedPreferences == null || mEdit == null) return;
        mEdit.putString(key, value);
        mEdit.apply();
    }

    public boolean getBoolean(String key) {
        if (mSharedPreferences == null) return false;
        return mSharedPreferences.getBoolean(key, false);
    }

    public void saveBoolean(String key, boolean bo) {
        if (mSharedPreferences == null || mEdit == null) return;
        mEdit.putBoolean(key, bo);
        mEdit.apply();
    }
}
