package com.example.yooho.zerostart.black.theme_factory;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;

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

    public Uri getUriBitmap(int resId) {
        if (true) return null;
        if (proxyResource == null) return null;
        String entryName = context.getResources().getResourceEntryName(resId);
        String dirPath = Environment.getExternalStorageDirectory() + File.separator + "webp" + File.separator;
        int patchResId = proxyResource.getIdentifier(entryName, "drawable", pkgName );
        String webpName = dirPath + entryName + ".webp";
        File webpFile = new File(webpName);

        Uri resultUri;
        if (!webpFile.exists()) {
            InputStream is = proxyResource.openRawResource(patchResId);
            byte[] b = new byte[1024];
            FileOutputStream fos = null;
            int length;
            try {
                Log.e("SS", "Create   ,,,,,,,,");
                fos = new FileOutputStream(webpFile);
                while ((length = is.read(b)) > 0) {
                    fos.write(b, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                    if (fos != null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        resultUri = Uri.fromFile(webpFile);
        return resultUri;
    }

    public Drawable getdrawable(int resId) {

        Drawable originD = context.getResources().getDrawable(resId);
        if (proxyResource == null) return originD;


        String resEntryName = context.getResources().getResourceEntryName(resId);
        int patchResId = proxyResource.getIdentifier(resEntryName, "drawable", pkgName);
        Drawable pathD = null;
        try {
            pathD = proxyResource.getDrawable(patchResId);
        } catch (Exception e) {
            pathD = originD;
        }
        return pathD;
    }

    public Bitmap getBitmap(int resId, int width, int height) {
        if (resId == 0) return null;

        Bitmap resultDrawable;
        if (proxyResource != null) {
            try {
                String resEntryName = context.getResources().getResourceEntryName(resId);
                int patchResId = proxyResource.getIdentifier(resEntryName, "drawable", pkgName);
//                resultDrawable = mProxyResource.getDrawable(patchResId);
                resultDrawable = decodeSampledBitmapFromResource(proxyResource, patchResId, width, height);
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), resultDrawable, null,null));
                Uri uri_1 = Uri.parse("android.resource://" + pkgName + "/drawable/abcs.png");
                Log.e("SS", "uri_1     ---   " + uri_1);
                Log.e("SS", "Uri     ---   " + uri);
                if (resultDrawable == null) {
                    resultDrawable = decodeSampledBitmapFromResource(context.getResources(), resId, width, height);
                    Uri uri2 = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), resultDrawable, null,null));
                    Log.e("SS", "Uri2     ---   " + uri2);
                }
            } catch (Exception e) {
                resultDrawable = decodeSampledBitmapFromResource(context.getResources(), resId, width, height);
                Log.e("SS", "getProxyDrawable() : ," + e.toString());
            }
        } else {
            resultDrawable = decodeSampledBitmapFromResource(context.getResources(), resId, width, height);
        }
        return resultDrawable;
    }

    interface FactoryListener {
        void updateAct();
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
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

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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
