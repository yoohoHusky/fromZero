package com.example.yooho.zerostart.black.theme_factory;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yooho.zerostart.R;

import java.io.File;
import java.lang.reflect.Field;

public class ThemeFactoryActivity extends FragmentActivity {

    private static final String SKIN_NAME = "BlackFantacy2.skin";
    private static final String SKIN_DIR = Environment
            .getExternalStorageDirectory() + File.separator + SKIN_NAME;
    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        try {
//            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
//            field.setAccessible(true);
//            field.setBoolean(getLayoutInflater(), false);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        LayoutInflater.

        mSkinInflaterFactory = new SkinInflaterFactory();
        getLayoutInflater().setFactory(mSkinInflaterFactory);
        SkinManager.getInstance().setListener(new SkinManager.FactoryListener() {
            @Override
            public void updateAct() {
                mSkinInflaterFactory.updateViewList();
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_demo2);
        MyListener listener = new MyListener();
        Button btn = findViewById(R.id.btn_day);
        btn.setOnClickListener(listener);
        findViewById(R.id.btn_night).setOnClickListener(listener);

    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_day) {
                SkinManager.getInstance().restoreDefaultTheme();
            } else if (v.getId() == R.id.btn_night) {
                File skin = new File(SKIN_DIR);
                SkinManager.getInstance().load(skin.getAbsolutePath());
            }
        }
    }


}
