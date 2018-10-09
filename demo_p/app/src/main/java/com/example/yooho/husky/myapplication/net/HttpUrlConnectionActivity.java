package com.example.yooho.husky.myapplication.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.example.yooho.husky.myapplication.R;

import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoou on 2018/6/8.
 */

public class HttpUrlConnectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_http_urlconnection);

        new Thread(new Runnable() {
            @Override
            public void run() {
                postRequest();
            }
        }).start();
    }

    private void postRequest() {
        try {
            URL url = new URL("http://www.baidu.com");
            HttpURLConnection httpURLConnection = initURLConnection(url);
            if (httpURLConnection == null) {
                return;
            }
            List<BasicNameValuePair> list = getPostParamsList();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            appendPostParams(outputStream, list);
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            InputStream inputStream = httpURLConnection.getInputStream();
            String str = parseStream(inputStream);
            Log.e("SS", "urlConnect_code:  " + responseCode);
            Log.e("SS", "urlConnect_content:  " + str);
        } catch (Throwable e) {
            e.printStackTrace();
            Log.e("SS", e.toString());
        }
    }

    private HttpURLConnection initURLConnection(URL url) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            return urlConnection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void appendPostParams(OutputStream outputStream, List<BasicNameValuePair> list) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (BasicNameValuePair pair : list) {
            if (!TextUtils.isEmpty(sb)) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(sb.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    private List<BasicNameValuePair> getPostParamsList() {
        List<BasicNameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("name", "moon"));
        list.add(new BasicNameValuePair("password", "123"));
        return list;
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

}
