package com.example.yooho.zerostart.image;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yooho.zerostart.R;

public class ImageActivity  extends AppCompatActivity {
    private ImageView mIvImage;
    private ImageView mIvImage2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_demo);
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mIvImage2 = (ImageView) findViewById(R.id.iv_image2);


        RequestOptions options1 = new RequestOptions();
        options1.placeholder(R.drawable.abcs).error(R.drawable.flicker)
                .circleCrop()
                .skipMemoryCache(true);

        int picId = R.drawable.ez;
        Glide.with(this)
                .load(picId)
                .apply(options1)
                .into(mIvImage);

        Glide.with(this).
                load(picId)
                .into(mIvImage2) ;
    }
}
