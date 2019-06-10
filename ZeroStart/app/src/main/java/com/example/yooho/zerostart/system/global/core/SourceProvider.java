package com.example.yooho.zerostart.system.global.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sdbean.werewolf.R;
import com.sdbean.werewolf.global.GlobalSourceProvider;
import com.sdbean.werewolf.global.misc.SkinConstant;
import com.sdbean.werewolf.global.tools.GlobalTools;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

public class SourceProvider {

    private String pathPkgName;
    private Context mContext;
    private Resources mProxyResource;
    private Resources mOriginResources;
    private PackageManager pm;
    private static final String TAG = "SourceProvider";

    public SourceProvider(Context context) {
        mContext = context;
        mOriginResources =  context.getResources();
        pm = context.getPackageManager();
    }

    public void load(String patchPath) {
        if (TextUtils.isEmpty(patchPath)) {
            cleanSkinSource();
            return;
        }

        File skin = new File(patchPath);
        if(skin == null || !skin.exists()){
            String zipPath = patchPath + ".skin";
            File zipFile = new File(zipPath);
            if (zipFile.exists()) {
                GlobalTools.unzip(zipPath, zipFile.getParent());
            } else {
                Toast.makeText(mContext.getApplicationContext(), "" + GlobalSourceProvider.getString(R.string.global_script_name_2_3226) + "" + patchPath + "" + GlobalSourceProvider.getString(R.string.global_script_name_2_3225) + "", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "skin file path : " + patchPath + ", 路径无法获得指定的资源包, 已切换回默认资源");
                SourceManager.getInstance().changeSkinSource(0);
                return;
            }
        }

        SourceManager.getInstance().cleanGlide();   // 切换皮肤资源包，要清空glide缓存
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

                    PackageInfo patchInfo = pm.getPackageArchiveInfo(patchPkgPath, PackageManager.GET_ACTIVITIES);
                    pathPkgName = patchInfo.packageName;

                    // 通过反射创建assetManager，并调用addAssetPath，传入patchPath
                    AssetManager assetManager = AssetManager.class.newInstance();
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    addAssetPath.invoke(assetManager, patchPkgPath);
                    Resources patchResources = new Resources(assetManager, mOriginResources.getDisplayMetrics(), mOriginResources.getConfiguration());

                    return patchResources;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Resources resources) {
                mProxyResource = resources;
                SourceManager.getInstance().reRenderView();
            }
        }.execute(patchPath);
    }

    public void cleanSkinSource() {
        SourceManager.getInstance().cleanGlide();
        mProxyResource = null;
        pathPkgName = null;
        SourceManager.getInstance().reRenderView();
    }

    /***     ****/

    public String getProxyString(int resId) {
        String resultStr = mOriginResources.getString(resId);
        if (mProxyResource != null) {
            try {
                String resEntryName = mOriginResources.getResourceEntryName(resId);
                int patchResId = mProxyResource.getIdentifier(resEntryName, "string", pathPkgName);
                resultStr = mProxyResource.getString(patchResId);
            } catch (Exception e) {
                Log.e(TAG, "getProxyString() : ," + e.toString());
            }
        }
        return resultStr;
    }

    public int getProxyColor(int resId) {
        int resultColor = mOriginResources.getColor(resId);
        if (mProxyResource != null) {
            try {
                String resEntryName = mOriginResources.getResourceEntryName(resId);
                int patchResId = mProxyResource.getIdentifier(resEntryName, "color", pathPkgName);
                resultColor = mProxyResource.getColor(patchResId);
            } catch (Exception e) {
                Log.e(TAG, "getProxyColor() : ," + e.toString());
            }
        }
        return resultColor;
    }

    public Bitmap getProxyDrawable(int resId) {
        return getProxyDrawable(resId, 0, 0);
    }

    public Bitmap getProxyDrawable(int resId, int width, int height) {
        if (resId == 0) return null;
        Bitmap resultDrawable;
        if (mProxyResource != null) {
            try {
                String resEntryName = mOriginResources.getResourceEntryName(resId);
                int patchResId = mProxyResource.getIdentifier(resEntryName, "drawable", pathPkgName);
//                resultDrawable = mProxyResource.getDrawable(patchResId);
                resultDrawable = decodeSampledBitmapFromResource(mProxyResource, patchResId, width, height);
                if (resultDrawable == null) {
                    resultDrawable = decodeSampledBitmapFromResource(mOriginResources, resId, width, height);
                }
            } catch (Exception e) {
                resultDrawable = decodeSampledBitmapFromResource(mOriginResources, resId, width, height);
                Log.e(TAG, "getProxyDrawable() : ," + e.toString());
            }
        } else {
            resultDrawable = decodeSampledBitmapFromResource(mOriginResources, resId, width, height);
        }
        return resultDrawable;
    }

    public Uri getWebpUri(@IdRes int webpId) {
        if (webpId == 0) return null;

        Uri oriUri = Uri.parse("res:///" + webpId);
        if (mProxyResource == null) return oriUri;

        String entryName = mOriginResources.getResourceEntryName(webpId);
        int patchResId = mProxyResource.getIdentifier(entryName, "drawable", pathPkgName);
        if (patchResId == 0) return oriUri;

        String outFileName = SourceManager.getInstance().getSkinDirPath() + File.separator + SkinConstant.SKIN_DIR_PATH_WEBP + File.separator + entryName + ".webp";
        File webpFile = new File(outFileName);
        if (webpFile.exists()) return Uri.fromFile(webpFile);
        InputStream is = mProxyResource.openRawResource(patchResId);
        if (GlobalTools.saveStream2Local(is, outFileName)) return Uri.fromFile(webpFile);
        return oriUri;
    }

    public Uri getSoundUri(@IdRes int soundId) {
        if (soundId == 0) return null;

        if (mProxyResource == null) return null;

        String entryName = mOriginResources.getResourceEntryName(soundId);
        String type = mOriginResources.getResourceTypeName(soundId);
        int patchResId = mProxyResource.getIdentifier(entryName, type, pathPkgName);
        if (patchResId == 0) return null;

        String outFileName = SourceManager.getInstance().getSkinDirPath() + File.separator + SkinConstant.SKIN_DIR_PATH_WEBP + File.separator + entryName + ".mp3";
        File webpFile = new File(outFileName);
        if (webpFile.exists()) return Uri.fromFile(webpFile);
        InputStream is = mProxyResource.openRawResource(patchResId);
        if (GlobalTools.saveStream2Local(is, outFileName)) return Uri.fromFile(webpFile);
        return null;
    }


    private Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        if (reqWidth == 0 || reqHeight == 0) {
            return BitmapFactory.decodeResource(res, resId);
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        if (reqWidth == 0 || reqHeight == 0) {
            options.inSampleSize = 1;
        } else {
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;



        if (height > reqHeight || width > reqWidth) {
            //计算图片高度和我们需要高度的最接近比例值
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            //宽度比例值
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //取比例值中的较大值作为inSampleSize
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
