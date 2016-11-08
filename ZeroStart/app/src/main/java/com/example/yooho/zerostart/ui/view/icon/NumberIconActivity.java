package com.example.yooho.zerostart.ui.view.icon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 2016/10/25.
 */
public class NumberIconActivity extends FragmentActivity implements View.OnClickListener {

    private TextView numTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_icon);
        numTextView = (TextView) findViewById(R.id.iv_num_icon);
        findViewById(R.id.btn_increase).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_increase) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(2.0f, 1.0f, 2.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(600);
            scaleAnimation.setInterpolator(new AccelerateInterpolator());
            numTextView.startAnimation(scaleAnimation);
        }
    }
}
