package com.example.yooho.zerostart.SVG;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.example.demotest.mylibrary.ui.SVG.ControllableVectorImageVIew;
import com.example.demotest.mylibrary.ui.SVG.ProcessVectorImageVIew;
import com.example.yooho.zerostart.R;

public class SVGControlActivity extends AppCompatActivity {
    SeekBar seekBar;
    ControllableVectorImageVIew svgIv;
    ControllableVectorImageVIew svgIv1;
    ProcessVectorImageVIew svgIv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_control);
        MyBtnListener myBtnListener = new MyBtnListener();

        svgIv = findViewById(R.id.svg_control_iv);
        svgIv1 = findViewById(R.id.svg_control_iv_1);
        svgIv2 = findViewById(R.id.svg_control_iv_2);
        seekBar = findViewById(R.id.svg_seek);
        svgIv.setOnClickListener(myBtnListener);
        svgIv1.setOnClickListener(myBtnListener);
        findViewById(R.id.btn_svg_once).setOnClickListener(myBtnListener);
        findViewById(R.id.btn_svg_recycle).setOnClickListener(myBtnListener);

        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new MySeekListener());

    }

    private class MySeekListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            float fraction = progress * 1.0f / seekBar.getMax();
            svgIv.updateFraction(fraction);
            svgIv1.updateFraction(fraction);
            svgIv2.updateFraction(fraction);
        }


        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class MyBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_svg_once) {
                svgIv.executeOnceAnim();
                svgIv1.executeOnceAnim();
            } else if (v.getId() == R.id.btn_svg_recycle) {
                svgIv.executeRecycleAnim();
                svgIv1.executeRecycleAnim();
            } else if (v.getId() == R.id.svg_control_iv) {
                svgIv.executeOnceAnim();
            } else if (v.getId() == R.id.svg_control_iv_1) {
                svgIv1.executeOnceAnim();
            }
        }
    }

    @Override
    protected void onDestroy() {
        svgIv.onRelease();
        svgIv = null;
        super.onDestroy();
    }
}













