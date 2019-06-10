package com.example.yooho.zerostart.black.theme_factory;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.system.Tools;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ThemeFactoryActivity extends FragmentActivity {

    private static final String SKIN_NAME = "BlackFantacy2.skin";
    private static final String SKIN_DIR = Environment
            .getExternalStorageDirectory() + File.separator + SKIN_NAME;
    private SkinInflaterFactory mSkinInflaterFactory;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;

    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("SS", "msg 1");
                    Log.e("SS", "msg 1 " + msg);
                    iv2.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 2:
                    Log.e("SS", "msg 2");
                    Log.e("SS", "msg 2 " + msg);
                    iv3.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 3:
                    Log.e("SS", "msg 3");
                    Log.e("SS", "msg 3 " + msg);
                    iv4.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 4:
                    Glide.get(ThemeFactoryActivity.this).clearMemory();
                    break;
            }
        }
    };
    private SimpleDraweeView drawee_1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        try {
//            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
//            field.setAccessible(true);
//            field.setBoolean(getLayoutInflater(), false);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        LayoutInflater.

        mSkinInflaterFactory = new SkinInflaterFactory();
        getLayoutInflater().setFactory(mSkinInflaterFactory);
        SkinManager.getInstance().setListener(new SkinManager.FactoryListener() {
            @Override
            public void updateAct() {
                mSkinInflaterFactory.updateViewList();
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_demo2);
        MyListener listener = new MyListener();

        findViewById(R.id.btn_day).setOnClickListener(listener);
        findViewById(R.id.btn_night).setOnClickListener(listener);
        findViewById(R.id.btn_3).setOnClickListener(listener);

        drawee_1 = findViewById(R.id.drawee_1);

        iv1 = findViewById(R.id.iv_1);
        iv2 = findViewById(R.id.iv_2);
        iv3 = findViewById(R.id.iv_3);
        iv4 = findViewById(R.id.iv_4);
        Log.e("SS", Environment.getExternalStorageDirectory() + "");
        String str = "1!#!0!#!!#!/lll/pppp";
        String[] strAtt = str.split("!#!");
        Log.e("SS", strAtt.length + "");
        Log.e("SS", strAtt[0]);
        Log.e("SS", strAtt[1]);
        Log.e("SS", strAtt[2]);
        Log.e("SS", strAtt[3]);


        File file = new File("/storage/emulated/0/demoL/japan.skin.zip");
        Log.e("SS", "P " + file.exists());
        Tools.unzip("/storage/emulated/0/demoL/japan.skin.zip", "/storage/emulated/0/demoL/");


    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_day) {
                SkinManager.getInstance().restoreDefaultTheme();
////                myHandler.sendEmptyMessage(4);
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        Glide.get(ThemeFactoryActivity.this).clearDiskCache();
////                    }
//                }).start();
            } else if (v.getId() == R.id.btn_night) {
                File skin = new File(SKIN_DIR);
                SkinManager.getInstance().load(skin.getAbsolutePath());
//                myHandler.sendEmptyMessage(4);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Glide.get(ThemeFactoryActivity.this).clearDiskCache();
//                    }
//                }).start();
            } else if (v.getId() == R.id.btn_3) {
//                Log.e("SS", "click  ");
//                Glide.with(ThemeFactoryActivity.this).load(R.drawable.num_icon_bg)
//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                        .transform(new MyImageTrans(ThemeFactoryActivity.this))
//                        .listener(new RequestListener<Integer, GlideDrawable>() {
//                            @Override
//                            public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                Log.e("SS", "error");
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                Log.e("SS", " ok ");
//                                return false;
//                            }
//                        })
//                        .into(iv1);
                loadDrawable(drawee_1, R.drawable.create_room_animation);

            }
        }
    }

    class MyImageTrans extends BitmapTransformation {

        public MyImageTrans(Context context) {
            super(context);
            Log.e("SS", "new ImageTrans");
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Log.e("SS", "befor   -1");
            Bitmap pollBitmap = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
            Log.e("SS", "befor   0");
            Bitmap usrBitmap = SkinManager.getInstance().getBitmap(R.drawable.abcs, outWidth, outHeight);
            Message msg = Message.obtain();
            Message msg2 = Message.obtain();
            Message msg3 = Message.obtain();
            msg.what = 1;
            msg.obj = pollBitmap;
            msg2.what = 2;
            msg2.obj = toTransform;
            msg3.what = 3;
            msg3.obj = usrBitmap;
            myHandler.sendMessage(msg);
            myHandler.sendMessage(msg2);
            myHandler.sendMessage(msg3);

            return usrBitmap;
        }


        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    public void loadDrawable(SimpleDraweeView draweeView, @DrawableRes int resId) {
//        Uri uri = Uri.parse(imageTranslateUri(draweeView.getContext(), resId));
//        String dirPath = Environment.getExternalStorageDirectory() + File.separator;
//        File baseFile = new File("/storage/emulated/0/webp/create_room_animation.webp");

        Uri bitmapUri = SkinManager.getInstance().getUriBitmap(R.drawable.create_room_animation);
        Log.e("SS", "LOL   " + bitmapUri);
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(bitmapUri).setAutoPlayAnimations(true).setOldController(draweeView.getController()).build();
        draweeView.setController(controller);
    }


    public static void loadWebpImage(SimpleDraweeView draweeView, String imageUrl) {
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(imageUrl).setAutoPlayAnimations(true).setOldController(draweeView.getController()).build();
        draweeView.setController(controller);
    }

    public static String imageTranslateUri(Context context, int resId) {
        Resources r = context.getResources();
        String pkg = r.getResourcePackageName(resId);
        pkg = "com.example.android_skin_laoder_skin";
        String type = r.getResourceTypeName(resId);
        String entry = r.getResourceEntryName(resId);
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +"://" + pkg +"/" + type +"/" + entry);
        Log.e("SS", "pkg  :" + pkg);
        Log.e("SS", "type  :" + type);
        Log.e("SS", "entry  :" + entry);

        return uri.toString();
    }







}
