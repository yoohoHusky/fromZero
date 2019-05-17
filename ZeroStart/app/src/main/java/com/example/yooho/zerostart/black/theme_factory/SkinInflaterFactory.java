package com.example.yooho.zerostart.black.theme_factory;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SkinInflaterFactory implements LayoutInflater.Factory {
    public static final String NAME_SPACE = "http://schemas.android.com/android/skin";
    public static final String ATTR_SKIN_ENABLE	= "enable";
    private List<ViewObject> viewList;

    public SkinInflaterFactory() {
        viewList = new ArrayList<>();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        boolean isSkinEnable = attrs.getAttributeBooleanValue(NAME_SPACE, ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable) return null;


        View view = myCreateView(name, context, attrs);
        if (view == null) return null;

        if ("Button".equals(name)) parseSkinAttr(context, attrs, view);
        return view;
    }

    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            Log.e("SS", "attrName : " + attrName);
            if (!"background".equals(attrName)) continue;

            if (attrValue.startsWith("@")) {
                int resId = Integer.parseInt(attrValue.substring(1));  // 指定资源的ID
                String resType = context.getResources().getResourceTypeName(resId);     // 根据type类型，调用不同getColor，getDrawable方法:color
                String resEntryName = context.getResources().getResourceEntryName(resId);// 后面自己重新获取:colorPrimary
                String resName = context.getResources().getResourceName(resId);         // 不知道啥区别:com.example.yooho.zerostart:color/colorPrimary
                String pkgName = context.getResources().getResourcePackageName(resId);  // 真没用到:com.example.yooho.zerostart
                Log.e("SS", "resType : " + resType
                        + "\nresName : " + resName
                        + "\nresEntryName : " + resEntryName
                        + "\npkgName : " + pkgName);

                int proxyColor = SkinManager.getInstance().getColor(resId);
                viewList.add(new ViewObject(view, resId));
            }
        }
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

    class ViewObject {
        View view;
        int resId;

        ViewObject(View view, int resId) {
            this.view = view;
            this.resId = resId;
        }
    }

    public void updateViewList() {
        if (viewList == null) return;
        for (ViewObject obj : viewList) {
            int proxyColor = SkinManager.getInstance().getColor(obj.resId);
            obj.view.setBackgroundColor(proxyColor);
        }
    }
}
