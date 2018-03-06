package com.example.yooho.zerostart.net.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yooho.zerostart.R;

/**
 * Created by haoou on 2018/2/25.
 */

public class OkhttpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        findViewById(R.id.okhttp_btn1).setOnClickListener(this);
        findViewById(R.id.okhttp_btn2).setOnClickListener(this);
        findViewById(R.id.okhttp_btn3).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
//        String url = "https://api.thinkpage.cn/v3/weather/now.json?key=rot2enzrehaztkdk&location=guangzhou&language=zh-Hans&unit=c";
//        if (view.getId() == R.id.okhttp_btn1) {
//            OkHttpClient client = new OkHttpClient();
//            Request.Builder builder = new Request.Builder();
//            Request request = builder.get().url(url).build();
//            Call call = client.newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.e("SS", e.toString());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    Log.e("SS", response.body().string());
//                }
//            });
//        } else if (view.getId() == R.id.okhttp_btn2) {
//            OkHttpClient okHttpClient = new OkHttpClient();
//            Request.Builder builder = new Request.Builder();
//            FormBody.Builder formBui = new FormBody.Builder();
//            formBui.add("userName", "aaa");
//            FormBody form = formBui.build();
//            builder.post(form);
//            builder.url(url);
//
//            Call call = okHttpClient.newCall(builder.build());
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.e("SS", e.toString());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    Log.e("SS", response.code() + "    |   " + response.body().string());
//                }
//            });
//        } else if (view.getId() == R.id.okhttp_btn3) {
//            final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
//            OkHttpClient client = new OkHttpClient();
//            RequestBody requestBody = new RequestBody() {
//                @Override
//                public MediaType contentType() {
//                    return MEDIA_TYPE_MARKDOWN;
//                }
//
//                @Override
//                public void writeTo(BufferedSink sink) throws IOException {
//                    sink.writeUtf8("Numbers\n");
//                    sink.writeUtf8("-------\n");
//                    for (int i = 2; i <= 997; i++) {
//                        sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
//                    }
//                }
//
//                private String factor(int n) {
//                    for (int i = 2; i < n; i++) {
//                        int x = n / i;
//                        if (x * i == n) return factor(x) + " × " + i;
//                    }
//                    return Integer.toString(n);
//                }
//            };
//            Request request = new Request.Builder()
//                    .url("https://api.github.com/markdown/raw")
//                    .post(requestBody)
//                    .build();
//            Call call = client.newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.e("SS", e.toString());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    final String responseStr = response.body().string();
//                    Log.e("SS", response.code() + "    |   " + response.body().string());
//                }
//            });
//
//        }

    }
}
