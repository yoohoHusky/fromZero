package com.example.yooho.zerostart.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.ui.view.VerticalSeekBar;

/**
 * Created by yooho on 2017/2/23.
 */
public class VerticalSeekbarActivity extends FragmentActivity {
    VerticalSeekBar verticalSeekBar;
    SeekBar normalSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_seekbar);
        verticalSeekBar = (VerticalSeekBar) findViewById(R.id.vertical_seekbar);
        normalSeekBar = (SeekBar) findViewById(R.id.normal_seekbar);

        MyBtnListener myBtnListener = new MyBtnListener();
        findViewById(R.id.seekbar_add).setOnClickListener(myBtnListener);
        findViewById(R.id.seekbar_delete).setOnClickListener(myBtnListener);


        verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("SS", "当前进度值:" + progress + "  / 100 ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(VerticalSeekbarActivity.this, "触碰SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(VerticalSeekbarActivity.this, "放开SeekBar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class MyBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.seekbar_add) {
                int progress = verticalSeekBar.getProgress();
                progress += 2;
                progress = progress > verticalSeekBar.getMax() ? verticalSeekBar.getMax() : progress;
                verticalSeekBar.setProgress(progress);
                normalSeekBar.setProgress(progress);

            } else if (v.getId() == R.id.seekbar_delete) {
                int progress = verticalSeekBar.getProgress();
                progress -= 2;
                progress = progress < 0 ? 0 : progress;
                verticalSeekBar.setProgress(progress);
                normalSeekBar.setProgress(progress);
            }
        }
    }

}
