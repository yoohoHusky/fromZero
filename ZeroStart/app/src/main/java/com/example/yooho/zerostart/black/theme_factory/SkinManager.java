package com.example.yooho.zerostart.black.theme_factory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;

public class SkinManager {

    private static SkinManager instance;
    private String pkgName;
    private Context context;
    private Resources proxyResource;

    public static final String DEFALT_SKIN = "cn_feng_skin_default";
    public static final String PREF_CUSTOM_SKIN_PATH = "cn_feng_skin_custom_path";
    private FactoryListener mListener;

    private SkinManager() {}
    public static SkinManager getInstance() {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null){
                    instance = new SkinManager();
                }
            }
        }
        return instance;
    }

    public void init(Context ctx) {
        context = ctx.getApplicationContext();
    }


    public void setListener(FactoryListener listener) {
        mListener = listener;
    }

    private Resources getResources() {
        return proxyResource;
    }

    public void restoreDefaultTheme() {
        proxyResource = context.getResources();
        notifySkinUpdate();
    }

    public void load(){
        load(PREF_CUSTOM_SKIN_PATH);
    }

    @SuppressLint("StaticFieldLeak")
    public void load(String patchPath) {
        File skin = new File(patchPath);
        if(skin == null || !skin.exists()){
            Toast.makeText(context.getApplicationContext(), "请检查" + patchPath + "是否存在", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<String, Void, Resources>() {
            @Override
            protected Resources doInBackground(String... params) {
                String patchPkgPath =  params[0];
                File pkgFile = new File(patchPkgPath);
                if (pkgFile == null || !pkgFile.exists()) {
                    return null;
                }

                try {
                // 通过自己的context得到packageManager
                    // 用pm读取patchFile，得到信息：patchInfo，得到patchPkgName
                    PackageManager pm = context.getPackageManager();
                    PackageInfo patchInfo = pm.getPackageArchiveInfo(patchPkgPath, PackageManager.GET_ACTIVITIES);
                    pkgName = patchInfo.packageName;

                    // 通过反射创建assetManager，并调用addAssetPath，传入patchPath
                    AssetManager assetManager = AssetManager.class.newInstance();
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    addAssetPath.invoke(assetManager, patchPkgPath);


                    Resources originResources =  context.getResources();
                    Resources patchResources = new Resources(assetManager, originResources.getDisplayMetrics(), originResources.getConfiguration());

                    return patchResources;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Resources resources) {
                proxyResource = resources;
                notifySkinUpdate();
            }
        }.execute(patchPath);
    }

    private void notifySkinUpdate() {
        if  (mListener !=null) mListener.updateAct();
    }

    /**
     * 核心方法 int color = resources.getColor(resId);
     *
     * 但是本地resId，在patch中无法找到正确的资源
     * 需要在本地，将resId 转换成 resName：resEntryName = resources.getResourcesEntryName(resId)
     * entryName是两端可以通用的，然后用 patchResId = patchResources.getIdentifier(entryName, "resType", pkgName)
     *      patchResources 是通过new Resources创建的，传入了assetManager（已经关联了patchPkgPath）
     * patchResources.getColor(patchResId)
     *
     */
    public int getColor(int resId) {

        int originColor = context.getResources().getColor(resId);
        if (proxyResource == null) return originColor;


        String resEntryName = context.getResources().getResourceEntryName(resId);
        int patchResId = proxyResource.getIdentifier(resEntryName, "color", pkgName);
        int pathColor = 0;
        try {
            pathColor = proxyResource.getColor(patchResId);
        } catch (Exception e) {
            pathColor = originColor;
        }
        return pathColor;
    }

    interface FactoryListener {
        void updateAct();
    }
}
