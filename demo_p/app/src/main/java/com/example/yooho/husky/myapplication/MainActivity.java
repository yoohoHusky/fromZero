package com.example.yooho.husky.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.yooho.husky.myapplication.aidldemo.AIDLActivity;
import com.example.yooho.husky.myapplication.io.SplitFile;
import com.example.yooho.husky.myapplication.myservice.MySetvice;
import com.example.yooho.husky.myapplication.net.NetHostActivity;
import com.example.yooho.husky.myapplication.ui.PaintApiActivity;
import com.example.yooho.husky.myapplication.ui.demo.DemoActivity;

import sun.applet.Main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MyClickListener myClickListener = new MyClickListener();
        findViewById(R.id.btn_net_model).setOnClickListener(myClickListener);
        findViewById(R.id.btn_view).setOnClickListener(myClickListener);
        findViewById(R.id.btn_view_zfb).setOnClickListener(myClickListener);
        findViewById(R.id.btn_demo_aidl).setOnClickListener(myClickListener);
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_net_model) {
                Intent intent = new Intent(MainActivity.this, NetHostActivity.class);
                startActivity(intent);
            } else if (view.getId() == R.id.btn_view) {
                Intent intent = new Intent(MainActivity.this, PaintApiActivity.class);
                startActivity(intent);
            } else if(view.getId() == R.id.btn_view_zfb) {
                Intent intent = new Intent(MainActivity.this, DemoActivity.class);
                startActivity(intent);
            } else if(view.getId() == R.id.btn_demo_aidl) {
//                Intent intent = new Intent(MainActivity.this, AIDLActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(MainActivity.this, MySetvice.class);
                startService(intent);
            }
        }
    }
}
