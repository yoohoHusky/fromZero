package com.example.yooho.zerostart.system.global;

import android.app.Activity;
import android.content.Context;

import com.example.yooho.zerostart.system.global.core.SourceManager;
import com.example.yooho.zerostart.system.global.model.SkinBean;

/**
 * 将打好的apk，改名为japan，将japan压缩成japan.skin
 * 后端配置 name=japan， type=.skin
 */
public class GlobalSdkApi {

    /**
     * 初始化国际化资源替换sdk，该方法在需要展示差异化的资源前完成地调用，建议在application中完成初始化
     */
    public static void init(Context context) {
        SourceManager.getInstance().init(context);
    }

    /**
     * 开启每一个新activity页面，在其onCreate的super.onCreate()方法前必须完成调用
     * 建议放在baseActivity中完成
     */
    public static void jumpNewActivity(Activity activity) {
        SourceManager.getInstance().jumpNewActivity(activity);
    }

    /**
     * 切换已知资源包内容（0-默认中文，1-日本资源，2-印度资源）
     */
    public static boolean changeSkinSource(int skinType) {
        return SourceManager.getInstance().changeSkinSource(skinType);
    }

    /**
     * 切换未知的资源文件（即非上述已确定的产品形态，中国资源、印度资源、日本资源）
     * @SkinBean {@link SkinBean} 需要做好资源的声名
     */
    public static boolean changeCustomSkin(SkinBean bean) {
        return SourceManager.getInstance().changeCustomSkin(bean);
    }

    /**
     * 获取到当前皮肤包的type（0-默认中文，1-日本资源，2-印度资源）
     */
    public static int getCurrentSkinType() {
        return SourceManager.getInstance().getSkinType();
    }



}
