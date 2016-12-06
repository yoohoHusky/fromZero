package com.example.yooho.zerostart.ui.activity;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 2016/12/6.
 */
public class AddChooseViewActivity extends FragmentActivity {

    Button showBtn;
    Button testBtn0;
    Button testBtn1;

    FrameLayout childContainer0;
    RelativeLayout childContainer1;

    RelativeLayout chooseView;
    RelativeLayout chooseContainer;
    ListView chooseLv;
    private boolean showChoose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_choose_view);

        initView();
        initEvent();
    }

    private void initEvent() {
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddChooseViewActivity.this, "cilck", Toast.LENGTH_SHORT).show();
                switch (v.getId()) {
                    case R.id.add_view_btn:
                        showChoose = !showChoose;
                        openAnimation(showChoose);
                        break;
                    case R.id.test_btn0:
                        break;
                    case R.id.test_btn1:
                        break;
                }
            }
        };
        showBtn.setOnClickListener(btnClickListener);
        testBtn0.setOnClickListener(btnClickListener);
        testBtn1.setOnClickListener(btnClickListener);

    }

    private void openAnimation(final boolean isOpen) {
        chooseView.setVisibility(View.VISIBLE);
        int height = chooseLv.getHeight();

        int startY,endY;
        float startAlpha,endAlpha;

        if (isOpen) {
            startY = -height;
            startAlpha = 0;
        } else {
            startY = 0;
            startAlpha = 1.0f;
        }
        endY = -height - startY;
        endAlpha = 1.0f - startAlpha;

        chooseView.setTranslationY(startY);
        chooseView.animate().translationY(endY).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                chooseView.setVisibility(isOpen ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void initView() {
        showBtn = (Button) findViewById(R.id.add_view_btn);
        childContainer0 = (FrameLayout) findViewById(R.id.child_container_0);
        childContainer1 = (RelativeLayout) findViewById(R.id.child_container_1);
        testBtn0 = (Button) findViewById(R.id.test_btn0);
        testBtn1 = (Button) findViewById(R.id.test_btn1);

        initChooseView();
    }

    private void initChooseView() {
        chooseView = (RelativeLayout) View.inflate(this, R.layout.layout_add_choose_view, null);
        chooseContainer = (RelativeLayout) chooseView.findViewById(R.id.add_view_lv_container);
        chooseLv = (ListView) chooseView.findViewById(R.id.add_view_lv);
        chooseLv.setAdapter(new ChooseAdapter());

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.topMargin = 10;
        chooseView.setVisibility(View.INVISIBLE);
        childContainer0.addView(chooseView, layoutParams);
    }

    class ChooseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                textView = new TextView(AddChooseViewActivity.this);
                textView.setBackgroundColor(Color.parseColor("#6688FF88"));
            } else {
                textView = (TextView) convertView;
            }
            textView.setText("position :" + position);
            return textView;
        }
    }
}
