package com.example.yooho.zerostart.system.global.core;


import com.example.yooho.zerostart.system.global.privatebean.GlobalRefreshBean;
import com.example.yooho.zerostart.system.global.privatebean.SourceAttrModel;

import java.util.ArrayList;

import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_ATTR_NAME_BG;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_ATTR_NAME_HINT;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_ATTR_NAME_SRC;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_ATTR_NAME_TEXT;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_ATTR_TYPE_COLOR;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_ATTR_TYPE_DRAWABLE;
import static com.example.yooho.zerostart.system.global.misc.SkinConstant.SKIN_ATTR_TYPE_STRING;


public class ViewRecordManager {

    private ArrayList<SourceAttrModel> mViewList;
    private ArrayList<GlobalRefreshBean> mImageViewList;
    private ArrayList<String> mValidAttrNameList;
    private ArrayList<String> mValidEntryTypeList;

    public ViewRecordManager() {
        mViewList = new ArrayList<SourceAttrModel>();
        mImageViewList = new ArrayList<>();

        mValidAttrNameList = new ArrayList<>();
        mValidAttrNameList.add(SKIN_ATTR_NAME_SRC);
        mValidAttrNameList.add(SKIN_ATTR_NAME_BG);
        mValidAttrNameList.add(SKIN_ATTR_NAME_TEXT);
        mValidAttrNameList.add(SKIN_ATTR_NAME_HINT);

        mValidEntryTypeList = new ArrayList<>();
        mValidEntryTypeList.add(SKIN_ATTR_TYPE_STRING);
        mValidEntryTypeList.add(SKIN_ATTR_TYPE_COLOR);
        mValidEntryTypeList.add(SKIN_ATTR_TYPE_DRAWABLE);
//        mValidEntryTypeList.add(SKIN_ATTR_TYPE_ID);
    }

    public boolean isValidAttrName(String attrName) {
        return mValidAttrNameList.contains(attrName);
    }

    public boolean isValidEntryType(String entryType) {
        return mValidEntryTypeList.contains(entryType);
    }

    public void recordView(SourceAttrModel model){
        mViewList.add(model);
    }

    public void recordImageView(GlobalRefreshBean bean){
        mImageViewList.add(bean);
    }

    public void cleanRecord() {
        if (mViewList != null && !mViewList.isEmpty()) {
            mViewList.clear();
        }
        if (mImageViewList != null && !mImageViewList.isEmpty()) {
            mImageViewList.clear();
        }
    }

    public void updateRecordedView() {
        if (mViewList != null && !mViewList.isEmpty()) {
            for (SourceAttrModel model : mViewList) {
                model.renderSelf();
            }
        }
        if (mImageViewList != null && !mImageViewList.isEmpty()) {
            for (GlobalRefreshBean bean : mImageViewList) {
                bean.refreshSelf();
            }
        }
    }
}
