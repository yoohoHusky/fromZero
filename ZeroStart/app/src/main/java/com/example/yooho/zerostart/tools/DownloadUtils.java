package com.example.yooho.zerostart.tools;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.yooho.zerostart.fakebean.DownloadAppBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


public class DownloadUtils {
    private static final String TAG = "DownloadUtils";
    private static final int TIME_INTERVAL_PROCESS_UPDATE = 700;
    private static final int TIME_DELAY_PROCESS_UPDATE = 300;

    private static DownloadUtils mInst;
    private Timer mTimer;

    private DownloadStatusObserver mObserver;
    private MyDownloadReceiver mReceiver;

    private DownloadManager downloadManager;
    private CommonDownloadObserver mCommonObserver;
    private ArrayList<DownloadAppBean> downAppList;
    private WeakReference<Context> applicationContextRef;

    private DownloadUtils() {
        if (mReceiver == null) mReceiver = new MyDownloadReceiver();
        if (mObserver == null) mObserver = new DownloadStatusObserver();
        if (mTimer == null) mTimer = new Timer();
        downAppList = new ArrayList();
    }

    public static DownloadUtils getInst() {
        if (mInst == null) {
            synchronized (DownloadUtils.class) {
                if (mInst == null) {
                    mInst = new DownloadUtils();
                }
            }
        }
        return mInst;
    }

    public void registerListen(Context context, CommonDownloadObserver commonObserver) {
        Log.e(TAG, "Start monitor download manager");
        applicationContextRef = new WeakReference(context.getApplicationContext());
        if (downloadManager == null) downloadManager = (DownloadManager) applicationContextRef.get().getSystemService(Context.DOWNLOAD_SERVICE);
        mCommonObserver = commonObserver;
        registerReceiver(applicationContextRef.get());                  // 监听本应用 下载的取消、完成
        registerProviderObserver(applicationContextRef.get());          // 可以跨应用监听 下载的开始，ID
    }

    public void unRegisterListen() {
        Log.e(TAG, "Stop monitor download manager");
        if (applicationContextRef == null || applicationContextRef.get() == null) return;
        applicationContextRef.get().unregisterReceiver(mReceiver);
        applicationContextRef.get().getContentResolver().unregisterContentObserver(mObserver);
        mCommonObserver = null;
        mTimer.cancel();
    }


    /* --------  内部方法  -------- */
    private void registerReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(mReceiver, filter);
    }

    private void registerProviderObserver(Context context) {
        context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), false, mObserver);
    }

    private void updateBeanData(DownloadAppBean bean) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(bean.getId());
        Cursor cursor = downloadManager.query(query);
        if (cursor == null || !cursor.moveToFirst()) {      // 查询不到，表示被取消了
            bean.setLoadBytes(-1L);                         // 已经下载文件大小
            return;
        }

        bean.setLoadBytes(cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));      // 已经下载文件大小
        bean.setTotalBytes(cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)));            // 下载文件的总大小
        bean.setLocalPath(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));                         // 本地URI
    }

    private class MyDownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            DownloadAppBean currentBean = null;

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.getAction()) { // 取消、完成都走这个回调
                for (DownloadAppBean bean : downAppList) {
                    if (bean.getId() == downId) currentBean = bean;
                }
                if (currentBean == null) {
                    Log.e(TAG, "ERROR, currentBean is not in list" );
                    return;
                }
                updateBeanData(currentBean);            // 更新bytes
                judgeFinishStatus(currentBean);         // 根据loadBytes，更改status
            }
        }

        private void judgeFinishStatus(DownloadAppBean currentBean) {
            if (currentBean.getLoadBytes() <= 0) {
                currentBean.setStatus(-2);
                if (mCommonObserver != null) mCommonObserver.onDownloadCancel(currentBean);
            } else {
                currentBean.setStatus(-1);
                if (mCommonObserver != null) mCommonObserver.onDownloadComplete(currentBean);
            }
        }
    }

    // 不同应用都会执行这里的回调
    class DownloadStatusObserver extends ContentObserver {
        public DownloadStatusObserver() { super(null); }

        @Override
        public void onChange(boolean selfChange) {  // 只能获得自己应用的 下载数据
            DownloadManager.Query query = new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_PENDING);   // 自己应用内的才有计数
            Cursor cursor = downloadManager.query(query);
            getDownloadStart(cursor);
        }

        private void getDownloadStart(Cursor cursor) {
            if (cursor == null || !cursor.moveToFirst()) return;           // 收到通知，但不是自己应用的

            if (downAppList == null) downAppList = new ArrayList();
            DownloadAppBean bean = createDownloadBean(cursor);
            downAppList.add(bean);

            if (mCommonObserver != null) mCommonObserver.onDownloadStart(bean);                                    // 通知onStart
            mTimer = new Timer();
            mTimer.schedule(new MyTimeTasker(), TIME_DELAY_PROCESS_UPDATE, TIME_INTERVAL_PROCESS_UPDATE);       // 处理process更新
        }

        private DownloadAppBean createDownloadBean(Cursor cursor) {
            int id = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
            String webUrl = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
            DownloadAppBean bean = new DownloadAppBean(id, title, desc, webUrl);
            return bean;
        }
    }

    private class MyTimeTasker extends TimerTask {
        @Override
        public void run() {
            Log.e(TAG, "run   run  run ");
            Iterator<DownloadAppBean> iterator = downAppList.iterator();
            while (iterator.hasNext()) {
                DownloadAppBean bean = iterator.next();
                updateBeanData(bean);                           // 更新bytes
                if (bean.getStatus() < 0) iterator.remove();    // 根据status，判断是否remove
            }
            if (mCommonObserver != null && !downAppList.isEmpty()) mCommonObserver.onDownloadProcessChange(downAppList);
            if (downAppList.isEmpty()) mTimer.cancel();
        }
    }

    public interface CommonDownloadObserver {
        void onDownloadStart(DownloadAppBean downloadAppBean);
        void onDownloadProcessChange(ArrayList<DownloadAppBean> downloadAppBean);
        void onDownloadCancel(DownloadAppBean downloadAppBean);
        void onDownloadComplete(DownloadAppBean downloadAppBean);
    }
}
