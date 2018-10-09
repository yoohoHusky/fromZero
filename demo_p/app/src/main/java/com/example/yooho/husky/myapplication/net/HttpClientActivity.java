package com.example.yooho.husky.myapplication.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yooho.husky.myapplication.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoou on 2018/6/8.
 */

public class HttpClientActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_httpclient);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getNetRequest();
                postNetRequest();
            }
        }).start();

    }

    private void postNetRequest() {
        HttpPost httpPost = new HttpPost("http://www.baidu.com");
        httpPost.addHeader("Connection", "Keep-Alive");
        List<BasicNameValuePair> pairList = new ArrayList<>();
        pairList.add(new BasicNameValuePair("name", "moon"));
        pairList.add(new BasicNameValuePair("password", "1234"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairList));
            DefaultHttpClient httpClient = getHttpClient();
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            InputStream inputStream = response.getEntity().getContent();
            String contentStr = parseStream(inputStream);
            inputStream.close();
            Log.e("SS", "post-code:  " + statusCode + "\npost-content:  " + contentStr);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private void getNetRequest() {
        DefaultHttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        httpGet.addHeader("Connection", "Keep-Alive");
        try {
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            InputStream inputStream = response.getEntity().getContent();
            String contentStr = parseStream(inputStream);
            inputStream.close();
            Log.e("SS", "code:  " + statusCode + "\ncontent:  " + contentStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String parseStream(InputStream inputStream) throws IOException{
        if (inputStream == null) {
            return "";
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private DefaultHttpClient getHttpClient() {
        BasicHttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 15000);
        HttpConnectionParams.setSoTimeout(params, 15000);
        HttpConnectionParams.setTcpNoDelay(params, true);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(params);
        return defaultHttpClient;
    }
}
