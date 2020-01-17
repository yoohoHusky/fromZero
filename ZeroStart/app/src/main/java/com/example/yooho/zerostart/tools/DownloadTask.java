package com.example.yooho.zerostart.tools;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class DownloadTask {
    private static final String INSTALL_AUTHORITY = "fileprovider";
    private static final String TYPE_INSTALL_FILE = "application/vnd.android.package-archive";
    private static final int TIME_INTERVAL_PROCESS_UPDATE = 700;
    private String mAuthorityStr;
    private Timer mTimer;
    private DownloadObserver mObserver;

    private String mShowTitle;
    private String mShowDesc;
    private WeakReference<Context> mContextRef;
    private String mDownloadUrl;
    private File mSaveFile;
    private DownloadManager downloadManager;
    private long mDownloadId;
    private MyDownloadReceiver mReceiver;
    private DownloadManager.Request request;

    public DownloadTask(Context context, String url, String saveName){
        this(context, url, saveName, null, null);
    }

    public DownloadTask(Context context, String url, String saveName, String title, String desc) {
        this(context, url, saveName, null, title, desc);
    }

    public DownloadTask(Context context, String url, String saveName, DownloadObserver observer, String title, String desc) {
        mContextRef = new WeakReference(context.getApplicationContext());
        mDownloadUrl = url;
        mShowTitle = TextUtils.isEmpty(title) ? "下载title" : title;
        mShowDesc = TextUtils.isEmpty(desc) ? "下载desc" : title;
        mSaveFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), saveName);
        mAuthorityStr = context.getApplicationInfo().packageName + "." + INSTALL_AUTHORITY;
        mObserver = observer;
        initTools();
    }

    private void initTools() {
        mTimer = new Timer();
        downloadManager = (DownloadManager) mContextRef.get().getSystemService(Context.DOWNLOAD_SERVICE);
        request = buildRequest(mDownloadUrl, mShowTitle, mShowDesc, mSaveFile);
    }

    public void exeDownload() {
        removeOldApk();
        if (downloadManager != null) mDownloadId = downloadManager.enqueue(request);
        if (mObserver != null) mObserver.onStartDownload();
        mTimer.schedule(new MyTimeTasker(), 0, TIME_INTERVAL_PROCESS_UPDATE);       // 处理process更新
        registerReceiver(mContextRef.get());                                              // 注册取消、完成监听
    }

    public void cancel() {
        downloadManager.remove(mDownloadId);
    }

    private void removeOldApk() {
        if (mSaveFile != null && mSaveFile.exists() && mSaveFile.isFile()) {
            mSaveFile.delete();
        }
    }

    private DownloadManager.Request buildRequest(String url, String title, String desc, File saveFile) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedOverRoaming(false);   //移动网络情况下是否允许漫游
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);  //在通知栏中显示，默认就是显示的
        request.setTitle(title);                                    // 设置title
        request.setDescription(desc);                               // 设置desc
        request.setVisibleInDownloadsUi(true);                      // TODO: 2020-01-16
        request.setDestinationUri(Uri.fromFile(saveFile));          // 设置save的路径
        return request;
    }

    private void registerReceiver(Context context) {
        if (mReceiver == null) mReceiver = new MyDownloadReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        context.registerReceiver(mReceiver, filter);
    }

    private void handleDownloadComplete() {
        DownloadManager.Query query = new DownloadManager.Query();
//        query.setFilterByStatus(STATUS_SUCCESSFUL);
        query.setFilterById(mDownloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst() && DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) installApk();
    }

    private void installApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW); //用于显示用户的数据。比较通用，会根据用户的数据类型打开相应的Activity。
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkUri;
        if (Build.VERSION.SDK_INT >= 24) {  //Android 7.0以上要使用FileProvider
            apkUri = FileProvider.getUriForFile(mContextRef.get(), mAuthorityStr, mSaveFile);   //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件

        } else {
            apkUri = Uri.fromFile(mSaveFile);
        }
        intent.setDataAndType(apkUri, TYPE_INSTALL_FILE);       // 标识该文件是用来安装的类型
        mContextRef.get().startActivity(intent);
    }


    private class MyDownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downId != mDownloadId) return;

            mTimer.cancel();
            context.unregisterReceiver(this);
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.getAction()) { // 取消、完成都走这个回调
                int[] code = getBytesAndStatus(downId);
                if (code[0] > 0) {
                    if (mObserver != null) mObserver.onDownloadComplete();
                    handleDownloadComplete();
                } else {
                    if (mObserver != null) mObserver.onDownloadCancel();
                }
            }
        }
    }

    private class MyTimeTasker extends TimerTask {
        @Override
        public void run() {
            int[] data = getBytesAndStatus(mDownloadId);
            if (data[1] < 0) return;
            if (mObserver != null) mObserver.onProcessUpdate(data[0] / (float)data[1]);
        }
    }

    private int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{-1, -1, 0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));    //已经下载文件大小
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));           //下载文件的总大小
                bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));                            //下载状态
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bytesAndStatus;
    }

    public interface DownloadObserver {
        void onStartDownload();
        void onProcessUpdate(float process);
        void onDownloadComplete();
        void onDownloadCancel();
    }
}






