package com.example.yooho.zerostart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.yooho.zerostart.tools.download.DownloadAppBean;
import com.example.yooho.zerostart.tools.ApkFileUtils;
import com.example.yooho.zerostart.tools.download.DownloadTask;
import com.example.yooho.zerostart.tools.download.DownloadTaskWatcher;
import com.example.yooho.zerostart.ui.view.processbar.GlitterProcessBar;

import java.util.ArrayList;

/**
 * Created by yooho on 16/10/9.
 */
public class DownloadModelActivity extends Activity {

    private static final int LOAD_RESTART = 101;
    private static final int LOAD_RECYCLE = 102;
    private static final String TAG = "DownloadModelActivity";

    GlitterProcessBar glitterProcessBar;
    private int loadProcess;
    private int mStatus;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mStatus != GlitterProcessBar.STATUS_DOWNLOADING) return;
            if (msg.what == LOAD_RESTART) {
                Message obtain = Message.obtain(this);
                obtain.what = LOAD_RECYCLE;
                sendMessageDelayed(obtain, 200);
            } else if (msg.what == LOAD_RECYCLE){
                if (loadProcess <= GlitterProcessBar.MAX_PROCESS) {
                    loadProcess  = loadProcess + 2;
                    glitterProcessBar.setProcess(loadProcess);
                    Message obtain = Message.obtain(this);
                    obtain.what = LOAD_RECYCLE;
                    sendMessageDelayed(obtain, 200);
                } else {
                    mStatus = GlitterProcessBar.STATUS_DOWNLOAD_FINISH;
                }

            }
        }
    };
    private MyClickListener listener;
    private MyDownloadObserver downloadObser;
    private MyCommonDownloadObserver commonDownObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        initTools();
        initFindView();
        initViewClick();
    }

    private void initFindView() {
        glitterProcessBar = findViewById(R.id.glitterProcess);
        findViewById(R.id.download_btn_0).setOnClickListener(listener);
        findViewById(R.id.download_btn_01).setOnClickListener(listener);
        findViewById(R.id.download_btn_02).setOnClickListener(listener);

        findViewById(R.id.download_btn_1).setOnClickListener(listener);
        findViewById(R.id.download_btn_2).setOnClickListener(listener);
    }

    private void initViewClick() {
        glitterProcessBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SS", mStatus + "");
                switch (mStatus) {
                    case GlitterProcessBar.STATUS_DOWNLOAD_FINISH:
                        return;
                    case GlitterProcessBar.STATUS_DOWNLOADING:
                        mStatus = GlitterProcessBar.STATUS_DOWNLOAD_PAUSE;
                        glitterProcessBar.setStop();
                        break;
                    case GlitterProcessBar.STATUS_DOWNLOAD_PAUSE:
                        mStatus = GlitterProcessBar.STATUS_DOWNLOADING;
                        glitterProcessBar.setRestart();
                        // 重新启动下载任务
                        handler.sendEmptyMessage(LOAD_RESTART);
                        break;
                }
            }
        });
    }

    private void initTools() {
        listener = new MyClickListener();
        downloadObser = new MyDownloadObserver();
        commonDownObserver = new MyCommonDownloadObserver();
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.download_btn_0) {
                String downlaodUrl = "http://down.shouji.kuwo.cn/star/mobile/kwplayer_ar_pcguanwangmobile.apk";
                DownloadTask downTask = new DownloadTask(DownloadModelActivity.this, downlaodUrl, "save1.apk", downloadObser, "down title 1", "Pl desc 1");
                downTask.exeDownload();
            } else if (v.getId() == R.id.download_btn_01) {
                String downlaodUrl = "http://dldir1.qq.com/music/clntupate/QQMusic72282.apk";
                DownloadTask downTask = new DownloadTask(DownloadModelActivity.this, downlaodUrl, "save2.apk", null, "down title 2", "Pl desc 2");
                downTask.exeDownload();
            } else if (v.getId() == R.id.download_btn_02) {
                String downlaodUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
                DownloadTask downTask = new DownloadTask(DownloadModelActivity.this, downlaodUrl, "save3.apk", null, "down title 3", "Pl desc 3");
                downTask.exeDownload();
            } else if (v.getId() == R.id.download_btn_1) {
                DownloadTaskWatcher.getInst().registerListen(DownloadModelActivity.this, commonDownObserver);
            } else if (v.getId() == R.id.download_btn_2) {
//                DownloadUtils.getInst().unRegisterListen();
                //  file:///storage/emulated/0/Android/data/com.example.yooho.zerostart/files/Download/save1.apk
                Uri uri = Uri.parse("file:///storage/emulated/0/Android/data/com.example.yooho.zerostart/files/Download/save1.apk");
                String path = ApkFileUtils.getFilePathByUri(DownloadModelActivity.this, uri);
                String packageName = ApkFileUtils.getApkInfo(DownloadModelActivity.this, path);
                boolean install = ApkFileUtils.isApkInstall(DownloadModelActivity.this, packageName);
            }
        }
    }

    class MyDownloadObserver implements DownloadTask.DownloadObserver {

        @Override
        public void onStartDownload() {
            Log.e("SS", "onStartDownload");
        }

        @Override
        public void onProcessUpdate(float process) {
            Log.e("SS", "onProcessUpdate   :  " + process);
        }

        @Override
        public void onDownloadComplete() {
            Log.e("SS", "onDownloadComplete");
        }

        @Override
        public void onDownloadCancel() {
            Log.e("SS", "onDownloadCancel");
        }
    }

    class MyCommonDownloadObserver implements DownloadTaskWatcher.CommonDownloadObserver{

        @Override
        public void onDownloadStart(DownloadAppBean downloadAppBean) {
            Log.e(TAG, "start  id =  " + downloadAppBean.getId());
            Log.e(TAG, "status  = " + downloadAppBean.getStatus());
        }

        @Override
        public void onDownloadProcessChange(ArrayList<DownloadAppBean> beanList) {
            for (int i = 0; i < beanList.size(); i++) {
                Log.e(TAG, "   ----------   ");
                Log.e(TAG, "update  id =  " + beanList.get(i).getId());
                Log.e(TAG, "float  = " + beanList.get(i).getProcess());
                Log.e(TAG, "status  = " + beanList.get(i).getStatus());
            }
        }

        @Override
        public void onDownloadCancel(DownloadAppBean downloadAppBean) {
            Log.e(TAG, "cancel  id = " + downloadAppBean.getId());
            Log.e(TAG, "status  = " + downloadAppBean.getStatus());
        }

        @Override
        public void onDownloadComplete(DownloadAppBean downloadAppBean) {
            Log.e(TAG, "complete  id = " + downloadAppBean.getId());
            Log.e(TAG, "status  = " + downloadAppBean.getStatus());
        }
    }
}
