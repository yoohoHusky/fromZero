package com.example.yooho.zerostart.ui.view.icon;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 2016/10/25.
 */
public class EmptyActivity extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setEnterTransition(new Explode());
        setContentView(R.layout.activity_empty);
    }
}
