package com.example.yooho.zerostart.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.ui.view.weather.WeatherDemoActivity;

public class DiyViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_view);

        initFindView();
    }

    private void initFindView() {
        MyListener listener = new MyListener();
        findViewById(R.id.diy_weather).setOnClickListener(listener);
        findViewById(R.id.diy_ruler).setOnClickListener(listener);
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.diy_weather) {
                startActivity(new Intent(DiyViewActivity.this, WeatherDemoActivity.class));
            } else if (v.getId() == R.id.diy_ruler) {
                startActivity(new Intent(DiyViewActivity.this, DiyRuleActivity.class));
            }
        }
    }
}
