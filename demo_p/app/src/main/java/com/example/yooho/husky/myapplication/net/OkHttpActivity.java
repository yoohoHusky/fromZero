package com.example.yooho.husky.myapplication.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.yooho.husky.myapplication.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by haoou on 2018/6/10.
 */

public class OkHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_okhttp);

        OkhttpClickListener listener = new OkhttpClickListener();
        findViewById(R.id.btn_okhttp_async_get).setOnClickListener(listener);
        findViewById(R.id.btn_okhttp_sync_get).setOnClickListener(listener);
        findViewById(R.id.btn_okhttp_async_post).setOnClickListener(listener);



    }

    private class OkhttpClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_okhttp_async_get:
                    doAsyncGet();
                    break;
                case R.id.btn_okhttp_sync_get:
                    doSyncGet();
                    break;
                case R.id.btn_okhttp_async_post:
                    doAsyncPost();
                default:
                    break;
            }
        }
    }

    private void doAsyncGet() {
        OkHttpClient httpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().get();
        final Request request = builder.url("http://www.baidu.com").build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("SS", "async get : " + e.toString());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                Log.e("SS", "async get : " + response.body().string());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(OkHttpActivity.this, "async成功", Toast.LENGTH_LONG).show();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void doSyncGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request build = builder.url("http://www.baidu.com").build();
                try {
                    Call call = okHttpClient.newCall(build);
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        Log.e("SS", "sync get : " + response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(OkHttpActivity.this, "sync成功", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doAsyncPost() {
        OkHttpClient httpClient = new OkHttpClient();

        com.squareup.okhttp.RequestBody postForm = new FormEncodingBuilder()
                .add("size", "10")
                .build();

        final Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .post(postForm)
                .build();

        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("SS", "async post : " + e.toString());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                Log.e("SS", "async post : " + response.body().string());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(OkHttpActivity.this, "async post 成功", Toast.LENGTH_LONG).show();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void doJsonPost() {
        RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new byte[3]);

        RequestBody imageBody = RequestBody.create(MediaType.parse("image/png"), new File(""));
        MultipartBody build = new MultipartBody.Builder()
                .addFormDataPart("file", "head_image", imageBody)
                .addFormDataPart("name", "yooho")
                .build();

    }
}
