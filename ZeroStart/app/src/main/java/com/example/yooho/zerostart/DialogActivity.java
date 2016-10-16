package com.example.yooho.zerostart;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

/**
 * Created by yooho on 16/10/12.
 */
public class DialogActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Dialog dialog = new Dialog(this, R.style.TransparentDialog);
//        dialog.setContentView(R.layout.live_download_deleting);
        View inflate = View.inflate(this, R.layout.live_download_deleting, null);
        dialog.setContentView(inflate);
        dialog.show();
    }
}
