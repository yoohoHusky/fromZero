package com.example.yooho.zerostart.ui.view.icon;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 2016/10/25.
 */
public class NumberIconActivity extends FragmentActivity implements View.OnClickListener {

    private TextView tweenTv;
    private ImageView tweenImg;
    private TextView valueTv;

    private ImageView objectImg;
    private ImageView circularImg;
    private ImageView actShareImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_icon);
        tweenTv = (TextView) findViewById(R.id.tween_text);
        tweenImg = (ImageView) findViewById(R.id.tween_pic);
        objectImg = findViewById(R.id.object_img);
        valueTv = findViewById(R.id.value_tv);
        circularImg = findViewById(R.id.circular_img);
        actShareImg =  findViewById(R.id.im_activity_share);


        findViewById(R.id.btn_tween_1).setOnClickListener(this);
        findViewById(R.id.btn_tween_2).setOnClickListener(this);

        findViewById(R.id.btn_value).setOnClickListener(this);
        findViewById(R.id.btn_object).setOnClickListener(this);

        findViewById(R.id.btn_circular).setOnClickListener(this::onClick);

        findViewById(R.id.btn_activity).setOnClickListener(this::onClick);
        findViewById(R.id.btn_act_explode).setOnClickListener(this::onClick);
        findViewById(R.id.btn_act_slide).setOnClickListener(this::onClick);
        findViewById(R.id.btn_act_share).setOnClickListener(this::onClick);
        findViewById(R.id.btn_vector).setOnClickListener(this::onClick);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tween_1) {
            doTweenAnim();
        } else if (v.getId() == R.id.btn_tween_2){
            doTweenAnim2();
        } else if (v.getId() == R.id.btn_value) {
            doValueAnim();
        } else if (v.getId() == R.id.btn_object){
            doObjectAnim();
        } else if (v.getId() == R.id.btn_circular) {
            doCircular();
        } else if (v.getId() == R.id.btn_activity){
            doActivityChange();
        } else if (v.getId() == R.id.btn_act_explode) {
            doActivityExplode();
        } else if (v.getId() == R.id.btn_act_slide) {
            doActivitySlide();
        } else if (v.getId() == R.id.btn_act_share) {
            doActivityShare();
        } else if (v.getId() == R.id.btn_vector) {
            startActivity(new Intent(NumberIconActivity.this, VectorActivity.class));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doActivityExplode() {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void doActivitySlide() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doActivityShare() {
        Intent intent = new Intent(this, EmptyShareActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(actShareImg, "share_icon")).toBundle());
    }

    private void doActivityChange() {
        startActivity(new Intent(this, EmptyActivity.class));
        overridePendingTransition(R.anim.activity_demo_enter, R.anim.activity_demo_exit);       // enter动画是给下一个页面的，exit动画是给自己页面的
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doCircular() {
        Animator animator = ViewAnimationUtils.createCircularReveal(circularImg, circularImg.getWidth()/2, circularImg.getHeight()/2, circularImg.getWidth(), 0);
        animator.setDuration(1000);
        animator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doObjectAnim() {
        ObjectAnimator objectAnim = ObjectAnimator.ofFloat(objectImg, "rotationY", 0, 359);
        objectAnim.setRepeatMode(ValueAnimator.REVERSE);
        objectAnim.setDuration(1000);
        objectAnim.setRepeatCount(1);
        objectAnim.setInterpolator(new DecelerateInterpolator());
        objectAnim.start();

        // 设置path
//        ObjectAnimator.ofFloat(objectImg, View.X, View.Y, new Path());
    }

    private void doValueAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float fraction = animation.getAnimatedFraction();
                int value = (int) animation.getAnimatedValue();
                valueTv.setText(value + "  : " + fraction);
            }
        });
        valueAnimator.start();
    }


    private void doTweenAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(2.0f, 1.0f, 2.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(600);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        tweenTv.startAnimation(scaleAnimation);
        tweenImg.startAnimation(scaleAnimation);
    }

    private void doTweenAnim2() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.tween_trance);
        tweenTv.startAnimation(anim);
        tweenImg.startAnimation(anim);
    }


}
