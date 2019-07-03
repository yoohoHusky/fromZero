package com.example.yooho.zerostart.system.global.misc;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.yooho.zerostart.system.global.core.SourceManager;
import com.example.yooho.zerostart.system.global.model.SkinBean;
import com.example.yooho.zerostart.system.global.tools.SkinSpHelper;

import java.io.File;

import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_DIR_INDIA;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_DIR_JAPAN;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_DIR_OTHER;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_FILE_INDIA_NAME;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_FILE_JAPAN_NAME;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_SP_KEY_VERSION_INFO;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_VERSION_INFO_SPLIT_STR;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.TYPE_CHINA_DEFAULT;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.TYPE_INDIA;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.TYPE_JAPAN;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.TYPE_OTHER;

public class SkinMapHandler {

    private SkinBean mSkinBean;
    private String mCacheDir;
    private static final String TAG = "SkinMapHandler";

    public SkinMapHandler(Context context) {
        mCacheDir = context.getExternalCacheDir().getPath();
//        mCacheDir = WerewolfApplication.WEREWOLF_RESOUCE;
    }

    public boolean changeSkin(SkinBean skin) {
        if (!checkSkinValid(skin)) return false;
        if (!checkNeedUpdate(skin)) return false;

        mSkinBean = skin;
        SourceManager.getInstance().getSourceProvider().load(getSkinPath(skin));
        saveVersionInfo(skin);
        return true;
    }

    public int getSkinType() {
        if (mSkinBean == null) return 0;
        return mSkinBean.getType();
    }

    public int getDescCode() {
        if (mSkinBean == null) return 0;
        return mSkinBean.getDescCode();
    }

    public String getSkinDirPath() {
        return getSkinDirPath(mSkinBean);
    }

    private String getSkinDirPath(SkinBean bean) {
        if (bean == null) return "";
        switch (bean.getType()) {
            case TYPE_CHINA_DEFAULT:
                return "";
            case TYPE_INDIA:
                return mCacheDir + File.separator + SKIN_DIR_INDIA + File.separator + bean.getDescCode();
            case TYPE_JAPAN:
                return mCacheDir + File.separator + SKIN_DIR_JAPAN + File.separator + bean.getDescCode();
            case TYPE_OTHER:
                return mCacheDir + File.separator + SKIN_DIR_OTHER + File.separator + bean.getDescCode();
        }
        return "";
    }

    public String getSkinPath(SkinBean bean) {
        if (bean == null) return "";
        switch (bean.getType()) {
            case TYPE_CHINA_DEFAULT:
                return "";
            case TYPE_JAPAN:
                return getSkinDirPath(bean) + File.separator + SKIN_FILE_JAPAN_NAME;
            case TYPE_INDIA:
                return getSkinDirPath(bean) + File.separator + SKIN_FILE_INDIA_NAME;
            case TYPE_OTHER:
                return bean.getPatchPath();
            default:
                return "";
        }
    }

    private boolean checkSkinValid(SkinBean skin) {
        if (skin == null) return false;

        switch (skin.getType()){
            case TYPE_CHINA_DEFAULT:
                return true;
            case TYPE_INDIA:
            case TYPE_JAPAN:
            case TYPE_OTHER:
                String path = getSkinPath(skin);
                String skinPath = path + ".skin";
                if (TextUtils.isEmpty(path) || !(new File(path).exists() || new File(path + ".skin").exists())) {
                    Log.e(TAG, "checkSkinValid error, patch file have some problem, skin info: " + skin.toString() + ", path = " + path);
                    return false;
                }
                return true;
            default:
                Log.e(TAG, "checkSkinValid error, type is not expect, skin info: " + skin.toString());
                return false;
        }
    }

    private boolean checkNeedUpdate(SkinBean skin) {
        if (mSkinBean == null) {
            return skin.getType() != TYPE_CHINA_DEFAULT;
        } else {
            if (mSkinBean.getType() != skin.getType() || mSkinBean.getDescCode() != skin.getDescCode()) {
                return true;
            }
        }
        return false;
    }

    public boolean loadRecordSkin() {
        String recordInfo = SkinSpHelper.getInst().getStringValue(SKIN_SP_KEY_VERSION_INFO, "");
        if (TextUtils.isEmpty(recordInfo)) {
            return true;
        }

        // 判断sp存储的格式是否正确
        String[] strArr = recordInfo.split(SKIN_VERSION_INFO_SPLIT_STR);
        if (strArr.length != 4) {
            Log.e(TAG, "skin record info not valid, info:" + recordInfo);
            return false;
        }
        int type = Integer.parseInt(strArr[0]);
        if (type == TYPE_CHINA_DEFAULT) return true;
        if (type == TYPE_INDIA || type == TYPE_JAPAN) {
            return changeSkin(new SkinBean(type));
        }
        if (type == TYPE_OTHER) {
            return changeSkin(new SkinBean(Integer.parseInt(strArr[1]), strArr[2], strArr[3]));
        }
        return false;
    }

    private void saveVersionInfo(SkinBean skin) {
        String versionStr = skin.getType() + SKIN_VERSION_INFO_SPLIT_STR
                + skin.getDescCode() + SKIN_VERSION_INFO_SPLIT_STR
                + skin.getDescStr() + SKIN_VERSION_INFO_SPLIT_STR
                + skin.getPatchPath();
        SkinSpHelper.getInst().putStringValue(SKIN_SP_KEY_VERSION_INFO, versionStr);
    }


}
