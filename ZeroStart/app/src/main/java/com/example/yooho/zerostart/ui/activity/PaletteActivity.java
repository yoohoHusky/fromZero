package com.example.yooho.zerostart.ui.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;

import com.example.yooho.zerostart.R;

public class PaletteActivity extends AppCompatActivity {
    private View mDecorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
//        new AsyncTaskCompat();
//        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.palette_src_1);
        Palette.from(BitmapFactory.decodeResource(getResources(), R.drawable.palette_src_1)).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch1 = palette.getLightVibrantSwatch();
                Palette.Swatch swatch2 = palette.getDarkMutedSwatch();

                Log.e("SS", swatch1.getPopulation() + "");
                Log.e("SS", swatch1.getRgb() + "");
                Log.e("SS", swatch1.getBodyTextColor() + "");
                Log.e("SS", swatch1.getTitleTextColor() + "");


                findViewById(R.id.color_01).setBackgroundColor(swatch1.getPopulation());
                findViewById(R.id.color_11).setBackgroundColor(swatch1.getRgb());
                findViewById(R.id.color_21).setBackgroundColor(swatch1.getBodyTextColor());
                findViewById(R.id.color_31).setBackgroundColor(swatch1.getTitleTextColor());

                findViewById(R.id.color_02).setBackgroundColor(swatch2.getPopulation());
                findViewById(R.id.color_22).setBackgroundColor(swatch2.getRgb());
                findViewById(R.id.color_12).setBackgroundColor(swatch2.getBodyTextColor());
                findViewById(R.id.color_32).setBackgroundColor(swatch2.getTitleTextColor());
            }
        });


    }
}
