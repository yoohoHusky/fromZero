package com.example.yooho.husky.myapplication.ui.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yooho.husky.myapplication.R;

/**
 * Created by haoou on 2018/8/7.
 */

public class DemoActivity extends AppCompatActivity {
    EditText mEditText;
    Button mShowBtn;
    ZFBBoard mZfbBorder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_zfb);
        MyListener myListener = new MyListener();

        mEditText = findViewById(R.id.ed_score);
        mShowBtn = findViewById(R.id.btn_score_show);
        mZfbBorder = findViewById(R.id.zfb_border);

        mShowBtn.setOnClickListener(myListener);

    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_score_show) {
                if (mEditText == null) {
                    return;
                }
                String score = mEditText.getText().toString().trim();
                mZfbBorder.updateScore(Integer.parseInt(score));
//                mZfbBorder.updateScore(40);
            }
        }
    }
}
