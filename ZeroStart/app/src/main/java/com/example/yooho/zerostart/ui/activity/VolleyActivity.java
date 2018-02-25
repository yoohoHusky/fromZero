package com.example.yooho.zerostart.ui.activity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yooho.zerostart.R;

import java.util.Map;

/**
 * Created by haoou on 2018/2/24.
 */

public class VolleyActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_volley);
        ((Button)findViewById(R.id.volley_btn1)).setOnClickListener(this);

        initVolley();
    }

    private void initVolley() {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

    }


    @Override
    public void onClick(View view) {
        String url = "https://api.thinkpage.cn/v3/weather/now.json?key=rot2enzrehaztkdk&location=guangzhou&language=zh-Hans&unit=c";
        if (view.getId() == R.id.volley_btn1) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new MyVolleyListener(), new MyVolleyErrorListener());
            stringRequest.setTag("aab");
            DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(defaultRetryPolicy);
            mRequestQueue.add(stringRequest);
            mRequestQueue.start();
        } else if (view.getId() == R.id.volley_btn2) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new MyVolleyListener(), new MyVolleyErrorListener());
            stringRequest.setTag("aab");
            DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(defaultRetryPolicy);
            mRequestQueue.add(stringRequest);
            mRequestQueue.start();
        }
    }

    private class MyVolleyListener implements Response.Listener<String> {

        @Override
        public void onResponse(String s) {
            Log.e("SS", s);
        }
    }

    private class MyVolleyErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("SS", "error   :  " + volleyError.toString());
        }
    }


}
