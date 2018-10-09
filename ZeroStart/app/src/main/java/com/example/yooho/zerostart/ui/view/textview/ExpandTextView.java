package com.example.yooho.zerostart.ui.view.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by haoou on 2018/10/8.
 *
 * 一、需要解决的问题
 * 1. 文字高亮
 * 2. 文字点击
 * 3. 连接转换
 * 4. 展开收起（颜色、点击、自动添加）
 * 5. 文字的展开与折叠
 *
 * 二、备选方案：
 * 1. 文字高亮
 *  1. onDraw时候对文字处理
 *  2. TextUtil里面有个url文字转换
 * 2. 文字点击
 *  1. 文字上浮另外一层，用以点击使用
 * 3. 连接转换
 *  1. 通过reg检测
 * 4. 展开收起（颜色、点击、自动添加）
 *  1. 用textMeasure来测量文字行数，在后面动态增加"展开"
 *  2.
 * 5. 文字展开与折叠
 *  1.
 *
 *
 */

@SuppressLint("AppCompatCustomView")
public class ExpandTextView extends TextView {
    public ExpandTextView(Context context) {
        super(context);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
