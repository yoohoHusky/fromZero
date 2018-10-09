package com.example.yooho.husky.myapplication.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yooho.husky.myapplication.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by haoou on 2018/6/10.
 */

public class VolleyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_volley);

        //1.创建request对象
        final RequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
        //2.创建request队列
        final StringRequest mStringRequest = new StringRequest(Request.Method.GET, "http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SS", "volley   :" + response);
                    }
                }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("SS", "volley   :" + error.getMessage(), error);
                }
        });
        //3.将request添加进队列
        requestQueue.add(mStringRequest);
        EventBus.getDefault().register();
    }
}
