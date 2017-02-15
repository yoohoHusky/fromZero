package com.example.yooho.zerostart.screenshotter;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yooho.zerostart.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TakeScreenShotActivity extends AppCompatActivity {

    private static final int REQUEST_MEDIA_PROJECTION = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;

    private static final String SCREENSHOTS_DIR_NAME = "Screenshots";
    private static final String SCREENSHOT_FILE_NAME_TEMPLATE = "Screenshot_%s.png";

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private int screenWidth;
    private int screenHeight;

    private static final String TAG = "TakeScreenShotActivity";
    private RelativeLayout mScreenContianer;
    private ImageView mScreenPicIv;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_screen_shot);
        initView();
        initData();
        mScreenContianer.setVisibility(View.GONE);
        doShotScreen();
    }

    private void doShotScreen() {
        if (isStoragePermissionGranted()) {
//            takeScreenshot();
            takeScreenshot2();
        } else {
            requestStoragePermissions();
        }
    }

    private void takeScreenshot2() {
        index = index + 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);

        String fname = "/sdcard/" + sdf.format(new Date()) + ".png";

        View view = mScreenContianer.getRootView();

        view.setDrawingCacheEnabled(true);

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        if (bitmap != null) {
            System.out.println("bitmap got !");

            try {

                long mImageTime = System.currentTimeMillis();
                String imageDate = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.ENGLISH).format(new Date(mImageTime));
                String mImageFileName = String.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate);
                File mScreenshotDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), SCREENSHOTS_DIR_NAME);
                String mImageFilePath = new File(mScreenshotDir, mImageFileName).getAbsolutePath();
                FileOutputStream out = new FileOutputStream(mImageFilePath);

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                System.out.println("file " + fname + "output done.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("bitmap is NULL !");
        }

    }




    private void initData() {
        getScreenSize();
    }

    private void initView() {
        mScreenContianer = (RelativeLayout) findViewById(R.id.screen_pic_container);
        mScreenPicIv = (ImageView) findViewById(R.id.screen_pic_iv);

    }

    private void getScreenSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void takeScreenshot() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager)
                getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        startActivityForResult(
                mediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Screenshotter.getInstance()
                            .setSize(screenWidth, screenHeight)
                            .takeScreenshot(TakeScreenShotActivity.this, resultCode, data, new ScreenshotCallback() {
                                @Override
                                public void onScreenshot(Bitmap bitmap) {
                                    Toast.makeText(TakeScreenShotActivity.this, "ok 1", Toast.LENGTH_SHORT).show();
                                    mScreenContianer.setVisibility(View.VISIBLE);
                                    mScreenPicIv.setImageBitmap(bitmap);

                                    // Prepare all the output metadata
                                    long mImageTime = System.currentTimeMillis();
                                    String imageDate = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.ENGLISH).format(new Date(mImageTime));
                                    String mImageFileName = String.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate);
                                    File mScreenshotDir = new File(Environment.getExternalStoragePublicDirectory(
                                            Environment.DIRECTORY_PICTURES), SCREENSHOTS_DIR_NAME);
                                    String mImageFilePath = new File(mScreenshotDir, mImageFileName).getAbsolutePath();

                                    if (!mScreenshotDir.exists()) {
                                        mScreenshotDir.mkdirs();
                                    }
                                    FileOutputStream out = null;
                                    try {
                                        out = new FileOutputStream(mImageFilePath);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            if (out != null) {
                                                out.flush();
                                                out.close();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    long dateSeconds = mImageTime / 1000;
                                    ContentValues values = new ContentValues();
                                    ContentResolver resolver = getContentResolver();
                                    values.put(MediaStore.Images.ImageColumns.DATA, mImageFilePath);
                                    values.put(MediaStore.Images.ImageColumns.TITLE, mImageFileName);
                                    values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, mImageFileName);
                                    values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, mImageTime);
                                    values.put(MediaStore.Images.ImageColumns.DATE_ADDED, dateSeconds);
                                    values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, dateSeconds);
                                    values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png");
                                    values.put(MediaStore.Images.ImageColumns.WIDTH, screenWidth);
                                    values.put(MediaStore.Images.ImageColumns.HEIGHT, screenHeight);
                                    values.put(MediaStore.Images.ImageColumns.SIZE, new File(mImageFilePath).length());
                                    Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                    finish();
                                    overridePendingTransition(0, R.anim.activity_zoom_exit);
                                }
                            });

                }
            }, 1000);
        } else {
            Toast.makeText(this, "You denied the permission.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isStoragePermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeScreenshot();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void requestStoragePermissions() {
        ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
        );
    }

}
