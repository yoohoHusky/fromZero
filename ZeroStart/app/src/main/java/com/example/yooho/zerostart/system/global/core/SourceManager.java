package com.example.yooho.zerostart.system.global.core;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.system.global.GlobalSourceProvider;
import com.example.yooho.zerostart.system.global.frame.SourceInflateFactory;
import com.example.yooho.zerostart.system.global.misc.SkinMapHandler;
import com.example.yooho.zerostart.system.global.model.SkinBean;
import com.example.yooho.zerostart.system.global.privatebean.GlobalRefreshBean;
import com.example.yooho.zerostart.system.global.privatebean.SourceAttrModel;
import com.example.yooho.zerostart.system.global.tools.SkinSpHelper;

import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_SP_KEY_CREATE_TIME;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.TYPE_INDIA;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.TYPE_JAPAN;

public class SourceManager {

    private static final String TAG = "SourceManager";

    private static SourceManager mInstance;
    private SourceProvider mSourceProvider;
    private Context mContext;
    private ViewRecordManager mRecordViewManager;
    private SkinMapHandler mSkinMapHandler;
    private SourceInflateFactory mSkinInflaterFactory;

    private SourceManager() {}
    public static SourceManager getInstance() {
        if (mInstance == null) {
            synchronized (SourceManager.class) {
                if (mInstance == null){
                    mInstance = new SourceManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        // 代码base层
        SkinSpHelper.getInst().init(context);

        // 代码业务层
        mSourceProvider = new SourceProvider(context);
        mRecordViewManager = new ViewRecordManager();
        mSkinInflaterFactory = new SourceInflateFactory();
        mSkinMapHandler = new SkinMapHandler(context);

        // 真正的开启实现
        if (!mSkinMapHandler.loadRecordSkin()) Toast.makeText(context, "加载资源包失败", Toast.LENGTH_SHORT).show();
    }

    public Context getContext() {
        return mContext;
    }

    public void jumpNewActivity(Activity activity) {
        if (mRecordViewManager != null && isNewCreateGroup()) mRecordViewManager.cleanRecord();
        if (mSkinInflaterFactory != null) activity.getLayoutInflater().setFactory(mSkinInflaterFactory);
    }

    public boolean changeSkinSource(int skinType) {
        SkinBean bean = new SkinBean(skinType);
        return changeCustomSkin(bean);
    }

    public boolean changeCustomSkin(SkinBean bean) {
        if (mContext == null || mSkinMapHandler == null || bean == null) return false;
        return mSkinMapHandler.changeSkin(bean);
    }

    public void recordView(SourceAttrModel attrModel) {
        if (mRecordViewManager != null) mRecordViewManager.recordView(attrModel);
    }

    public void recordView(GlobalRefreshBean bean) {
        if (mRecordViewManager != null) mRecordViewManager.recordImageView(bean);
    }

    public SourceProvider getSourceProvider() {
        return mSourceProvider;
    }

    public void reRenderView() {
        if (mRecordViewManager != null) mRecordViewManager.updateRecordedView();
    }

    public boolean isValidAttrName(String attrName) {
        if (mRecordViewManager == null) return false;
        return mRecordViewManager.isValidAttrName(attrName);
    }

    public boolean isValidEntryType(String entryType) {
        if (mRecordViewManager == null) return false;
        return mRecordViewManager.isValidEntryType(entryType);
    }

    public boolean isNewCreateGroup() {
        long currentTime = System.currentTimeMillis();
        long lastTime = SkinSpHelper.getInst().getLongValue(SKIN_SP_KEY_CREATE_TIME, 0);
        SkinSpHelper.getInst().putLongValue(SKIN_SP_KEY_CREATE_TIME, currentTime);
        if (currentTime - lastTime > 1000L * 3) {   // 开启新页面间隔3秒钟
            return true;
        } else {
            return false;
        }
    }

    public boolean isUseSkin() {
        if (mSkinMapHandler == null) return false;
        return mSkinMapHandler.getSkinType() != 0;
    }

    public int getSkinType() {
        if (mSkinMapHandler == null) return 0;
        return mSkinMapHandler.getSkinType();
    }

    public int getSkinDescCode() {
        if (mSkinMapHandler == null) return 0;
        return mSkinMapHandler.getDescCode();
    }



    public String getSkinDirPath() {
        if (mSkinMapHandler == null) return "";
        return mSkinMapHandler.getSkinDirPath();
    }

    public String getSkinPath(int type) {
        if (mSkinMapHandler == null) return "";

        if (type == TYPE_JAPAN || type == TYPE_INDIA) {
            return mSkinMapHandler.getSkinPath(new SkinBean(type));
        } else {
            return "";
        }
    }

    public void cleanGlide() {
        if (mContext == null) return;
        new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(mContext).clearDiskCache();
                }
            }).start();
        Glide.get(mContext).clearMemory();
    }


}
