package com.example.yooho.zerostart.fakebean;

public class DownloadAppBean {
//                int byte0 = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));    //已经下载文件大小
//                int byte1 = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));           //下载文件的总大小
//                int byte2 = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));                            //下载状态
//                String byte21 = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));                            //
//                String byte3 = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
//                String byte5 = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));                   // 本地地址
//                String byte6 = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));                         // 远端下载地址
//                String byte4 = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIAPROVIDER_URI));           // 空
////                    String byte6 = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));            // 不允许被访问

    long id;
    long loadBytes;
    long totalBytes;
    String title;
    String desc;
    String webUrl;
    String localPath;
    int status;     // 默认是0表示running，-1表示完成，-2表示取消;

    public DownloadAppBean(long id, String title, String desc, String webUrl) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.webUrl = webUrl;
        this.status = 0;
    }

    public void setLoadBytes(long loadBytes) {
        this.loadBytes = loadBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public long getId() {
        return id;
    }

    public long getLoadBytes() {
        return loadBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getProcess() {
        if (totalBytes <= 0) return -1f;
        return loadBytes / (float)totalBytes;
    }
}
