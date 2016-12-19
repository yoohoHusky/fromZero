package com.example.yooho.zerostart.ui.view.processbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.ui.BaseActivity;

/**
 * Created by yooho on 2016/12/9.
 */
public class DashBoardActivity extends BaseActivity {

    EditText editTv;
    ZFBDashBoard dashBoard;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfb_dashboard);
        dashBoard = (ZFBDashBoard) findViewById(R.id.dash_board);
        editTv = (EditText) findViewById(R.id.dash_et);
        btn = (Button) findViewById(R.id.dash_btn);
        btn.setOnClickListener(new ClickListener());
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String intStr = editTv.getText().toString();
            if (TextUtils.isEmpty(intStr)) {
                return;
            }
            try{

                int i = Integer.parseInt(intStr.toString());
                dashBoard.setCurrentNumAnim(i);
            } catch (Exception ex) {

            }
        }
    }
}
