package com.example.yooho.zerostart.screenshotter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.yooho.zerostart.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yooho on 2016/12/20.
 */
public class ScreenShotterAct extends FragmentActivity {

    public static final String ACTION_HIDE_BUTTON = "ACTION_HIDE_BUTTON";
    public static final String ACTION_SHOW_BUTTON = "ACTION_SHOW_BUTTON";
    private static final String SCREENSHOTS_DIR_NAME = "Screenshots";
    private static final String SCREENSHOT_FILE_NAME_TEMPLATE = "Screenshot_%s.png";

    private static final int REQUEST_SHOT_SCREEN_CODE = 1000;

    Button mBtn;
    ImageView iv0;
    ImageView iv1;
    private int screenWidth;
    private int screenHeight;
    private Bitmap bitmap;
    private Matrix mDisplayMatrix;
    private WindowManager mWindowManager;
    private Display mDisplay;
    private DisplayMetrics mDisplayMetrics;
    private Bitmap mScreenBitmap;
    private int index;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        mBtn = (Button) findViewById(R.id.screen_shot);
        iv0 = (ImageView) findViewById(R.id.screen_iv0);
        iv1 = (ImageView) findViewById(R.id.screen_iv1);


        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = index + 1;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);

                String fname = "/sdcard/" + sdf.format(new Date()) + ".png";
                View view;
                switch (index % 3) {
                    case 0:
                        view = mBtn;
                        break;
                    case 1:
                        view = iv1;
                        break;
                    case 2:
                        view = iv1.getRootView();
                        break;
                    default:
                        view = iv1.getRootView();
                }

                view.setDrawingCacheEnabled(true);

                view.buildDrawingCache();

                Bitmap bitmap = view.getDrawingCache();


                long mImageTime = System.currentTimeMillis();
                String imageDate = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.ENGLISH).format(new Date(mImageTime));
                String mImageFileName = String.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate);
                File mScreenshotDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), SCREENSHOTS_DIR_NAME);
                String mImageFilePath = new File(mScreenshotDir, mImageFileName).getAbsolutePath();
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(mImageFilePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                iv0.setImageBitmap(bitmap);


            }
        });
    }

    private float getDegreesForRotation(int value) {
        switch (value) {
            case Surface.ROTATION_90:
                return 360f - 90f;
            case Surface.ROTATION_180:
                return 360f - 180f;
            case Surface.ROTATION_270:
                return 360f - 270f;
        }
        return 0f;
    }


    private void getScreenSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    private void doShotScreen() {
        Intent it = new Intent(this, TakeScreenShotActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
//        startActivityForResult(it, REQUEST_SHOT_SCREEN_CODE);

    }
}
