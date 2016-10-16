package com.example.yooho.zerostart.ui.view.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.yooho.zerostart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yooho on 16/10/12.
 */
public class KeyTextViewSpan extends TextView {

    private int keyColor1;
    private int keyColor2;
    private int keyColor3;
    private ArrayList<KeyTextModel> mKeyModelList;
    private static String KEY_INFO_SEPARATE = " ";
    private static int KEY_DEFAULT_COLOR = Color.RED;
    private int[] mKeyColorList;

    public KeyTextViewSpan(Context context) {
        this(context, null);
    }
    public KeyTextViewSpan(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyTextViewSpan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributeValue(attrs);
    }

    /**
     * 可以在xml中预设值key关键字颜色
     * @param attrs
     */
    private void getAttributeValue(AttributeSet attrs) {
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.KeyTextViewSpan);
        keyColor1 = typedArray.getColor(R.styleable.KeyTextViewSpan_span_text_view_key_color1, KEY_DEFAULT_COLOR);
        keyColor2 = typedArray.getColor(R.styleable.KeyTextViewSpan_span_text_view_key_color2, KEY_DEFAULT_COLOR);
        keyColor3 = typedArray.getColor(R.styleable.KeyTextViewSpan_span_text_view_key_color3, KEY_DEFAULT_COLOR);
        init();
    }

    private void init() {
        mKeyModelList = new ArrayList<KeyTextModel>();
        mKeyColorList = new int[3];
        mKeyColorList[0] = keyColor1;
        mKeyColorList[1] = keyColor2;
        mKeyColorList[2] = keyColor3;
    }

    private void updateKeyModelList(String text, String keyText, boolean canRepeat) {
        if (mKeyModelList == null) {
            mKeyModelList = new ArrayList<>();
        } else if (!mKeyModelList.isEmpty()){
            mKeyModelList.clear();
        }
        String[] keys = keyText.split(KEY_INFO_SEPARATE);
        // 用来记录命中的index
        int colorIndex = 0;
        if (keys != null && keys.length > 0) {
            for (int i = 0; i < keys.length; i++) {
                KeyTextModel model = new KeyTextModel();
                model.keyText = keys[i];
                model.startIndexList = getKeyIndexList(text, keys[i], canRepeat);
                if (model.startIndexList != null) {
                    if (colorIndex >= mKeyColorList.length) {
                        model.color = mKeyColorList[mKeyColorList.length - 1];
                    } else {
                        model.color = mKeyColorList[colorIndex];
                    }
                    colorIndex ++;
                    mKeyModelList.add(model);
                }
            }
        }
    }

    private List<Integer> getKeyIndexList(String text, String key, boolean canRepeat) {
        List<Integer> startList = null;
        int index = text.indexOf(key, 0);
        if (index > -1) {
            startList = new ArrayList<>();
            startList.add(index);

            while (canRepeat && index > -1) {
                startList.add(index);
                index = text.indexOf(key, index + 1);
            }
        }

        return startList;
    }

    private boolean checkKeyTextValid(String keyText) {
        if (TextUtils.isEmpty(keyText)) {
            return false;
        } else {
            keyText = keyText.trim();
            return !TextUtils.isEmpty(keyText);
        }
    }


    private class KeyTextModel {
        List<Integer> startIndexList;
        int color;
        String keyText;
    }

    /**
     * 对外暴露的调用方法
     * @param text      需要展示的文本
     * @param keyText   需要标注颜色的关键词,多次用空格分开
     */
    public void setTextWithKey(String text, String keyText) {
        setTextWithKey(text, keyText, false);
    }


    /**
     * 对外暴露的调用方法
     * @param text      需要展示的文本
     * @param keyText   需要标注颜色的关键词,多次用空格分开
     * @param canRepeat 重复文字是否进行标色
     */
    public void setTextWithKey(String text, String keyText, boolean canRepeat) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (!checkKeyTextValid(keyText)) {
            setText(text);
            return;
        }
        keyText = keyText.trim();
        updateKeyModelList(text, keyText, canRepeat);
        if (mKeyModelList == null || mKeyModelList.isEmpty()) {
            setText(text);
            return;
        }
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(text);
        for (KeyTextModel keyModel: mKeyModelList) {
            for (int startIndex : keyModel.startIndexList) {
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(keyModel.color);
                spanBuilder.setSpan(foregroundColorSpan, startIndex,
                        startIndex + keyModel.keyText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        setText(spanBuilder);
    }

    /**
     * 对外暴露的设置关键字变化颜色的色值数组
     * @param keyColorValues  色值数组,应该是颜色值,不应该是颜色的引用值
     *                        for example: spanTV.setKeyClolorArray(new int[]{Color.BLUE, Color.GRAY});
     */
    public void setKeyClolorArray(int[] keyColorValues) {
        mKeyColorList = keyColorValues;
    }
}
