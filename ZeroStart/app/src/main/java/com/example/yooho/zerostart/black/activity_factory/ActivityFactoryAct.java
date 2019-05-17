package com.example.yooho.zerostart.black.activity_factory;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.yooho.zerostart.R;

public class ActivityFactoryAct extends AppCompatActivity {

    private static final String TAG = "SS";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }

            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.e(TAG, "parent:" + parent + ",name = " + name);
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++) {
                    Log.e(TAG, attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                }

                if (name.equals("TextView") || name.equals("ImageView")) {
                    name = "Button";
                }
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);
                return view;

            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_factory);

    }
}
