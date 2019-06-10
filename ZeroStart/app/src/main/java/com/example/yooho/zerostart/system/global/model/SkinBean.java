package com.example.yooho.zerostart.system.global.model;

import static com.sdbean.werewolf.global.misc.SkinConstant.TYPE_CHINA_DEFAULT;
import static com.sdbean.werewolf.global.misc.SkinConstant.TYPE_JAPAN;
import static com.sdbean.werewolf.global.misc.SkinConstant.TYPE_OTHER;

public class SkinBean {

    /**
     * 主键id，须保证唯一性
     */
    private int mType;

    /**
     * 扩展使用的int，如设定成  日文版本(1)，日本版本(2)，暂未使用
     */
    private int mDescCode;

    /**
     * 对descCode的文字补充描述
     */
    private String mDescStr;

    /**
     * 指定资源类型的skin包在手机中的路径
     * 格式为：/storage/emulated/0/Android/data/cache/other/{mDescCode}/{文件包名字}
     */
    private String mPatchPath;

    public SkinBean(int type) {
        mType = type;
        mDescCode = 0;
        mDescStr = "desc_default";
        mPatchPath = "default_path";
    }

    public SkinBean(int descCode, String descStr, String patchPath) {
        mType = TYPE_OTHER;
        mDescCode = descCode;
        mDescStr = descStr;
        mPatchPath = patchPath;
    }

    public static boolean isStandType(int type) {
        if (type >= TYPE_CHINA_DEFAULT && type <= TYPE_JAPAN) {
            return true;
        } else {
            return false;
        }
    }

    public int getType() {
        return mType;
    }

    public int getDescCode() {
        return mDescCode;
    }

    public String getDescStr() {
        return mDescStr;
    }

    public String getPatchPath() {
        return mPatchPath;
    }

    @Override
    public String toString() {
        return "{type=" + getType()
                + ", desCode=" + getDescCode()
                + ", desStr=" + getDescStr()
                + ", patchPath=" + getPatchPath() + "}";
    }
}
