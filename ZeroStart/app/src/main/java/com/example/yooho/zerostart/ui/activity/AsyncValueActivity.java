package com.example.yooho.zerostart.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by haoou on 2018/10/10.
 */

public class AsyncValueActivity extends AppCompatActivity {

    static int[] arr = new int[4];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int result = getValue();
        int count = 0;
        for (int i=0; i<4; i++) {
            arr[i] = i * 3;
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                arr[0] =  100;
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
        while (true) {
            try {
                Thread.sleep(500);
                Log.e("SSS", count++ + "   |   " + result + "");
                Log.e("SSS", count++ + "  |||  " + getValue() + "");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getValue() {
        return arr[0];
    }
}
