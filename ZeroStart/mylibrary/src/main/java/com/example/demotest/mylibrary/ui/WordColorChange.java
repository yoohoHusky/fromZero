package com.example.demotest.mylibrary.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class WordColorChange extends TextView {
    public WordColorChange(Context context) {
        super(context);
    }

    public WordColorChange(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setColorText() {
        String str = "一二山四五六七八九十";

        // 主要使用SpannableStringBuilder，ForegroundColorSpan两个类
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.RED);
        ssb.setSpan(fcs, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan fcs2 = new ForegroundColorSpan(Color.RED);
        ssb.setSpan(fcs2, 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan fcs3 = new ForegroundColorSpan(Color.RED);
        ssb.setSpan(fcs3, 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(ssb);
    }

}
