package com.example.yooho.zerostart.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yooho.zerostart.R;

public class PageUiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_ui);
        PageListener listener = new PageListener();
        findViewById(R.id.page_immersive).setOnClickListener(listener);
        findViewById(R.id.page_palette).setOnClickListener(listener);
    }


    class PageListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.page_immersive) {
                Intent intent = new Intent(PageUiActivity.this, ImmersiveActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.page_palette) {
                Intent intent = new Intent(PageUiActivity.this, PaletteActivity.class);
                startActivity(intent);
            }

        }
    }
}
