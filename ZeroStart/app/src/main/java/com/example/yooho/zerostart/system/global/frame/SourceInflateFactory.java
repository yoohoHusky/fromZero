package com.example.yooho.zerostart.system.global.frame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.sdbean.werewolf.global.core.SourceManager;
import com.sdbean.werewolf.global.privatebean.SourceAttrModel;

import static com.sdbean.werewolf.global.misc.SkinConstant.SKIN_ATTR_ENABLE;
import static com.sdbean.werewolf.global.misc.SkinConstant.SKIN_ATTR_REFRESH_SELF;
import static com.sdbean.werewolf.global.misc.SkinConstant.SKIN_NAME_SPACE;

public class SourceInflateFactory implements LayoutInflater.Factory {

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SKIN_NAME_SPACE, SKIN_ATTR_ENABLE, false);
        boolean reSelf = attrs.getAttributeBooleanValue(SKIN_NAME_SPACE, SKIN_ATTR_REFRESH_SELF, false);
        reSelf = false;
        if (!isSkinEnable) return null;

        View view = myCreateView(name, context, attrs);
        if (view == null) return null;

        parseSkinAttr(context, attrs, view, reSelf);
        return view;
    }

    private View myCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        try {
            // 判断是否是自定义view
            if (-1 == name.indexOf(".")) {
                // 不包含. 说明不是自定义view
                if ("View".equals(name)) {
                    view = myInflateView(context, name, "android.view.", attrs);
                }
                if (view == null) {
                    view = myInflateView(context, name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = myInflateView(context, name, "android.webkit.", attrs);
                }
            } else {
                // 包含. 说明是com.xx.xx形式的自定义view
                view = myInflateView(context, name, null, attrs);
            }
        } catch (Exception e) {

        }
        return view;
    }

    private View myInflateView(Context context, String name, String prefix, AttributeSet attrs) {
        View view = null;
        try {
            view = LayoutInflater.from(context).createView(name, prefix, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void parseSkinAttr(Context context, AttributeSet attrs, View view, boolean reSelf) {
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if (!checkAttrValid(context, attrName, attrValue)) continue;

            if (!attrValue.startsWith("@")) continue;
            int resId = Integer.parseInt(attrValue.substring(1));  // 指定资源的ID
            String resType = context.getResources().getResourceTypeName(resId);     // 根据type类型，调用不同getColor，getDrawable方法:color
            String resEntryName = context.getResources().getResourceEntryName(resId);// 后面自己重新获取:colorPrimary
//            String resName = context.getResources().getResourceName(resId);         // 不知道啥区别:com.example.yooho.zerostart:color/colorPrimary
//            String pkgName = context.getResources().getResourcePackageName(resId);  // 真没用到:com.example.yooho.zerostart

            SourceAttrModel.handleRender(view, attrName, resEntryName, resType, resId);
            if (reSelf) {
                SourceManager.getInstance().recordView(new SourceAttrModel(view, attrName, resEntryName, resType, resId));
            }
        }
    }

    private boolean checkAttrValid(Context context, String attrName, String attrValue) {
        if (!SourceManager.getInstance().isValidAttrName(attrName)) return false;
        if (!attrValue.startsWith("@")) return false;
        int resId = Integer.parseInt(attrValue.substring(1));  // 指定资源的ID
        if (resId == 0) return false;
        String resType = context.getResources().getResourceTypeName(resId);
        if (!SourceManager.getInstance().isValidEntryType(resType)) return false;
        return true;
    }

}
