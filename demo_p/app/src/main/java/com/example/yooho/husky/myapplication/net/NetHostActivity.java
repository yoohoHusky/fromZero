package com.example.yooho.husky.myapplication.net;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yooho.husky.myapplication.MainActivity;
import com.example.yooho.husky.myapplication.R;

/**
 * Created by haoou on 2018/6/8.
 */

public class NetHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_host);

        MyClickListener myClickListener = new MyClickListener();
        findViewById(R.id.btn_net_httpclient).setOnClickListener(myClickListener);
        findViewById(R.id.btn_net_urlconnect).setOnClickListener(myClickListener);
        findViewById(R.id.btn_net_volley_string).setOnClickListener(myClickListener);
        findViewById(R.id.btn_net_okthhp).setOnClickListener(myClickListener);
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_net_httpclient) {
                Intent intent = new Intent(NetHostActivity.this, HttpClientActivity.class);
                startActivity(intent);
            } else if (view.getId() == R.id.btn_net_urlconnect) {
                Intent intent = new Intent(NetHostActivity.this, HttpUrlConnectionActivity.class);
                startActivity(intent);
            } else if (view.getId() == R.id.btn_net_volley_string) {
                Intent intent = new Intent(NetHostActivity.this, VolleyActivity.class);
                startActivity(intent);
            } else if (view.getId() == R.id.btn_net_okthhp) {
                Intent intent = new Intent(NetHostActivity.this, OkHttpActivity.class);
                startActivity(intent);
            }
        }
    }

}
