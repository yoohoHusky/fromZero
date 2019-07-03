package com.example.yooho.zerostart.system.global.privatebean;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yooho.zerostart.system.global.GlobalSourceHelper;
import com.example.yooho.zerostart.system.global.GlobalSourceProvider;
import com.example.yooho.zerostart.system.global.core.SourceManager;
import com.example.yooho.zerostart.system.global.misc.SkinConstant;

public class SourceAttrModel {
    View mView;
    String mAttrName;        // 属性名
    String mResEntryName;    // 属性entryName
    String mAttrType;        // 属性值的类型
    int mResId;        // 值真实值
    public SourceAttrModel(View view, String attrName, String resEntryName, String attrType, int resId) {
        this.mView = view;
        this.mAttrName = attrName;
        this.mResEntryName = resEntryName;
        this.mAttrType = attrType;
        this.mResId = resId;
    }

    public void renderSelf() {
        handleRender(mView, mAttrName, mResEntryName, mAttrType, mResId);
    }

    public static void handleRender(View view, String attrName, String resEntryName, String attrType, int resId) {
        if (attrName.equals(SkinConstant.SKIN_ATTR_NAME_BG) && attrType.equals(SkinConstant.SKIN_ATTR_TYPE_DRAWABLE)) {      // background + drawable
            GlobalSourceHelper.loadImage(view, resId, true);
        } else if (attrName.equals(SkinConstant.SKIN_ATTR_NAME_BG) && attrType.equals(SkinConstant.SKIN_ATTR_TYPE_COLOR)) {     // src + color
            view.setBackgroundColor(SourceManager.getInstance().getSourceProvider().getProxyColor(resId));
        } else if (attrName.equals(SkinConstant.SKIN_ATTR_NAME_SRC) && attrType.equals(SkinConstant.SKIN_ATTR_TYPE_DRAWABLE)) {     // src + drawable
            if (view instanceof ImageView) {
                ImageView iv = (ImageView) view;
                GlobalSourceHelper.loadImage(iv, resId, true);
//                iv.setImageBitmap(SourceProxy.getBitmap(resId));
            }
        } else if (attrName.equals(SkinConstant.SKIN_ATTR_NAME_TEXT) && attrType.equals(SkinConstant.SKIN_ATTR_TYPE_STRING)){
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setText(GlobalSourceProvider.getString(resId));
            }
        } else if (attrName.equals(SkinConstant.SKIN_ATTR_NAME_HINT) && attrType.equals(SkinConstant.SKIN_ATTR_TYPE_STRING)){
            if (view instanceof EditText) {
                EditText et = (EditText) view;
                et.setHint(GlobalSourceProvider.getString(resId));
            }
        }
    }
}
