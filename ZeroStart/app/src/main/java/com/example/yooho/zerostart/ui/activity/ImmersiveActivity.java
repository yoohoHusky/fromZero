package com.example.yooho.zerostart.ui.activity;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.tools.NewStatusBarUtil;

public class ImmersiveActivity extends AppCompatActivity {
    private View mDecorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersive);
        NewStatusBarUtil.setTransparent(ImmersiveActivity.this);

        mDecorView = getWindow().getDecorView();

        ImmListener listener = new ImmListener();
        findViewById(R.id.imm_reset).setOnClickListener(listener);
        findViewById(R.id.imm_immersive).setOnClickListener(listener);
        findViewById(R.id.imm_translate).setOnClickListener(listener);
    }

    private void resetTheme() {
        mDecorView.setSystemUiVisibility(0);
        changeViewAlpha(findViewById(R.id.group1), false);
        changeViewAlpha(findViewById(R.id.group2), false);
    }

    private void changeViewAlpha(View view, boolean toTranslate) {
        if (toTranslate) {
            ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(300).start();
        } else {
            ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(300).start();
        }
    }



    private void changeImmersive() {
        Log.e("SS", mDecorView.getSystemUiVisibility() + "");
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        changeViewAlpha(findViewById(R.id.group1), true);
        changeViewAlpha(findViewById(R.id.group2), true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void changeTranslate() {
//        getSupportActionBar().hide();
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.parseColor("#bb333333"));
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        changeViewAlpha(findViewById(R.id.group1), false);
        changeViewAlpha(findViewById(R.id.group2), false);
    }

    class ImmListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imm_reset) {
              resetTheme();
            } else if (v.getId() == R.id.imm_immersive) {
                changeImmersive();
            } else if (v.getId() == R.id.imm_translate) {
                changeTranslate();
            }
        }
    }
}
