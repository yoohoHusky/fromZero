package com.example.yooho.zerostart.ui.view.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 2016/10/31.
 */
public class WeatherDemoActivity extends FragmentActivity{

    WeatherScrollView mWeatherScroll;
    OneDay24HourView mOneDay24HourView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_host);

        initView();
    }

    private void initView() {
        mWeatherScroll = (WeatherScrollView) findViewById(R.id.weather_weather_scroll);
        mOneDay24HourView = (OneDay24HourView) findViewById(R.id.weather_oneday_24hour_view);

        mWeatherScroll.setOneDay24HourView(mOneDay24HourView);

    }
}
