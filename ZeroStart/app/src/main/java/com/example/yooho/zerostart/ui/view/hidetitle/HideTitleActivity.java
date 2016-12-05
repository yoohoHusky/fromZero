package com.example.yooho.zerostart.ui.view.hidetitle;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 2016/11/17.
 *
 * 顶部移动:
 *         AnimatorSet animationSet = new AnimatorSet();
 *         animationSet.play(alpha).with(translationY);
 *         ObjectAnimator.ofFloat(titleIv, "alpha", floats2);
 *
 * 底部移动:
 *         RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
 *         layoutParams.setMargins(0, marginTop, 0, 0);
 *         contextView.setLayoutParams(layoutParams);
 *         contextView.invalidate();
 *
 */
public class HideTitleActivity extends FragmentActivity {

    LinearLayout titleContainer;
    ImageView titleIv;
    RelativeLayout contextView;
    Button btn;
    TextView titleTv;
    private boolean isup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_title);

//
        titleContainer = (LinearLayout) findViewById(R.id.title_container);
        titleIv = (ImageView) findViewById(R.id.title_iv);
        titleTv = (TextView) findViewById(R.id.title_tv);
        btn = (Button) findViewById(R.id.btn);
        contextView = (RelativeLayout) findViewById(R.id.context_container);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float[] floats = new float[2];
                float[] floats2 = new float[2];
                if (isup) {
                    floats[0] = 0.0f;
                    floats[1] = -titleIv.getHeight();
                    floats2[0] = 1.0f;
                    floats2[1] = 0.0f;
                } else {
                    floats[0] = -titleIv.getHeight();
                    floats[1] = 0.0f;
                    floats2[0] = 0.0f;
                    floats2[1] = 1.0f;
                }

                AnimatorSet animationSet = new AnimatorSet();
                ObjectAnimator alpha = ObjectAnimator.ofFloat(titleIv, "alpha", floats2);
                ObjectAnimator translationY = ObjectAnimator.ofFloat(titleIv, "translationY", floats);
                animationSet.setDuration(1000);
                animationSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!isup) {
                            setMargin(titleContainer.getHeight());
                        }
                        isup = !isup;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animationSet.play(alpha).with(translationY);
                animationSet.start();
                int height = titleContainer.getHeight();
                int height1 = titleIv.getHeight();
                if (isup) {
                    setMargin(titleContainer.getHeight() - titleIv.getHeight());
                } else {

                }
                titleTv.setText("con: " + height + "   iv: " + height1);

            }
        });
    }

    private void setMargin(int marginTop) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, marginTop, 0, 0);
        contextView.setLayoutParams(layoutParams);
        contextView.invalidate();
    }

}
