package com.example.yooho.zerostart.ui.view.icon;

import android.annotation.TargetApi;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;

import com.example.yooho.zerostart.R;

/**
 * Created by yooho on 2016/10/25.
 */
public class VectorActivity extends AppCompatActivity {

    ImageView vectorAnimIv;
    ImageView vectorStateIv;
    private boolean isTwitterChecked;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vector);
        MyClickListener listener = new MyClickListener();
        vectorAnimIv = findViewById(R.id.vector_animation);
        vectorStateIv = findViewById(R.id.vector_state_animation);
        vectorStateIv.setOnClickListener(listener);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startAnim(View view) {
        Drawable drawable = vectorAnimIv.getDrawable();
        ((Animatable) drawable).start();
    }


    class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.vector_state_animation) {
                isTwitterChecked = !isTwitterChecked;
                final int[] stateSet =
                        {android.R.attr.state_checked * (isTwitterChecked ? 1 : -1)};
                vectorStateIv.setImageState(stateSet, true);
            }
        }
    }

}
