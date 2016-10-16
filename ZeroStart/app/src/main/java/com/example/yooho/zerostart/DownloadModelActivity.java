package com.example.yooho.zerostart;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.yooho.zerostart.ui.view.processbar.GlitterProcessBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yooho on 16/10/9.
 */
public class DownloadModelActivity extends Activity {

    private static final int LOAD_RESTART = 101;
    private static final int LOAD_RECYCLE = 102;

    GlitterProcessBar glitterProcessBar;
    private int loadProcess;
    private int mStatus;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mStatus != GlitterProcessBar.STATUS_DOWNLOADING) {
                return;
            }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        String text = "abcdefgakla";
        String key = "a";


        List<Integer> startList = null;
        if (text.contains(key)) {
            int index = text.indexOf(key, 0);
            startList = new ArrayList<>();
            while (index > -1) {
                startList.add(index);
                index = text.indexOf(key, index + 1);
            }
        }


        glitterProcessBar = (GlitterProcessBar) findViewById(R.id.glitterProcess);

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

}
