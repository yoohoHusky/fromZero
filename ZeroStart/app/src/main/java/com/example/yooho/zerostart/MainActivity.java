package com.example.yooho.zerostart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.model_download).setOnClickListener(this);
        findViewById(R.id.model_dialog).setOnClickListener(this);
        findViewById(R.id.model_text_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.model_download) {
            startActivity(new Intent(this, DownloadModelActivity.class));
        } else if (v.getId() == R.id.model_dialog) {
            startActivity(new Intent(this, DialogActivity.class));
        } else if (v.getId() == R.id.model_text_view) {
            startActivity(new Intent(this, TextViewActivity.class));
        }
    }
}
