package com.example.yooho.zerostart.ui.view.line;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 2016/10/31.
 */
public class DynamicalLineActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamical_line);
        final DynamicalLine path_view = (DynamicalLine) findViewById(R.id.path);
        path_view.init();
        path_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                path_view.init();
            }
        });
    }
}
