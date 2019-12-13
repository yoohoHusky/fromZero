package com.example.yooho.zerostart.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yooho.zerostart.R;

public class NotifyUiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_ui);
        NoteListener listener = new NoteListener();

        findViewById(R.id.notify_snack_bar).setOnClickListener(listener);
    }


    class NoteListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.notify_snack_bar) {
                Intent intent = new Intent(NotifyUiActivity.this, NotifySnackBarActivity.class);
                startActivity(intent);
            }



        }
    }
}
