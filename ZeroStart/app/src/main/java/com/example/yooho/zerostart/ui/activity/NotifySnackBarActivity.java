package com.example.yooho.zerostart.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yooho.zerostart.R;

public class NotifySnackBarActivity extends AppCompatActivity {
    private LinearLayout rootLL;
    private static Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_bar);
        NoteListener listener = new NoteListener();

        rootLL = findViewById(R.id.list_root);
        findViewById(R.id.snack_1).setOnClickListener(listener);
        findViewById(R.id.snack_2).setOnClickListener(listener);
        findViewById(R.id.snack_3).setOnClickListener(listener);
        findViewById(R.id.snack_4).setOnClickListener(listener);
        findViewById(R.id.snack_5).setOnClickListener(listener);
    }


    class NoteListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Toast.makeText(NotifySnackBarActivity.this, "toast a ", Toast.LENGTH_SHORT).show();
            if (v.getId() == R.id.snack_1) {
                Snackbar.make(rootLL, "简单snackBar", Snackbar.LENGTH_SHORT).show();
            } else if (v.getId() == R.id.snack_2) {
                Snackbar.make(rootLL, "带动作的", Snackbar.LENGTH_LONG)
                        .setAction("点击啊", o -> {
                            Toast.makeText(NotifySnackBarActivity.this, "点击了哦", Toast.LENGTH_SHORT).show();
                            Log.e("SS", "点击了文字 2");
                        })
                        .show();
            } else if (v.getId() == R.id.snack_3) {
                Snackbar snack = Snackbar.make(rootLL, "定制图片", Snackbar.LENGTH_SHORT)
                        .setAction("点击啊 定制图片", o -> Log.e("SS", "点击了定制的action"));
                View snackView = snack.getView();
                snackView.setBackgroundResource(R.drawable.divider_bg);                                 // 设置背景图片
                ((TextView)snackView.findViewById(R.id.snackbar_text)).setTextColor(Color.GREEN);       // 设置提示文字颜色
                ((TextView)snackView.findViewById(R.id.snackbar_action)).setTextColor(Color.YELLOW);    // 设置按钮文字颜色
                snack.show();
            } else if (v.getId() == R.id.snack_4) {
                Snackbar snack = Snackbar.make(rootLL, "", Snackbar.LENGTH_SHORT);
                View snackView = snack.getView();
                snackView.setBackgroundColor(Color.TRANSPARENT);
                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout)snackView;

//                snackView.setBackgroundResource(R.drawable.abcs);       // 设置背景图片
                View addView = LayoutInflater.from(snackView.getContext()).inflate(R.layout.view_snack_demo, null);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.CENTER_VERTICAL;
                snackbarLayout.addView(addView, params);                                                // 定义背景图片，布局不能完全填满
                snack.show();
            } else if (v.getId() == R.id.snack_5) {
                Log.e("SS", "toast");
                Toast.makeText(NotifySnackBarActivity.this, "LMMLM", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
