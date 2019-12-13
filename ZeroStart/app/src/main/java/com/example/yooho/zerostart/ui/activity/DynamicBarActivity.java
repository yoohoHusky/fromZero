package com.example.yooho.zerostart.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yooho.zerostart.R;

public class DynamicBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_bar);
        DynamicBarListener listener = new DynamicBarListener();


        findViewById(R.id.model_1).setOnClickListener(listener);
        findViewById(R.id.model_2).setOnClickListener(listener);
        findViewById(R.id.model_3).setOnClickListener(listener);
    }

    class DynamicBarListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.model_1) {
                Intent intent = new Intent(DynamicBarActivity.this, DynamicBarActivity1.class);
                startActivity(intent);
            } else if (v.getId() == R.id.model_2) {
                Intent intent = new Intent(DynamicBarActivity.this, DynamicBarActivity2.class);
                startActivity(intent);
            } else if (v.getId() == R.id.model_3) {
                Intent intent = new Intent(DynamicBarActivity.this, DynamicBarActivity3.class);
                startActivity(intent);
            }
        }
    }
}
