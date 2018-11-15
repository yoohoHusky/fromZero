package com.example.yooho.zerostart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demotest.mylibrary.ui.WordColorChange;
import com.example.yooho.zerostart.ui.view.textview.KeyTextViewSpan;

/**
 * Created by yooho on 16/10/12.
 */
public class TextViewActivity extends Activity {

    EditText edText;
    EditText edKey;
    KeyTextViewSpan spanTV;
    WordColorChange wc;

    String textInfo;
    String keyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        edText = (EditText) findViewById(R.id.ed_text);
        edKey = (EditText) findViewById(R.id.ed_key);
        spanTV = (KeyTextViewSpan) findViewById(R.id.tv_div_span);
        wc = findViewById(R.id.tv_word_color);

        edText.addTextChangedListener(new TextEditChangeListener());
        edKey.addTextChangedListener(new KeyEditChangeListener());
        wc.setColorText();

        handleOriginTextView();

    }


    private void handleOriginTextView() {
        TextView originTextView = (TextView) findViewById(R.id.tv_div_origin);
        String stringInfo = "原生textView,123,一二三,abc";
        String keyInfo = "textView";
        int startIndex = stringInfo.indexOf(keyInfo);
        int endIndex = startIndex + keyInfo.length();
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(stringInfo);
        spanBuilder.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        originTextView.setText(spanBuilder);
    }

    class TextEditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s.toString())) {
                return;
            }
            textInfo = s.toString();
            spanTV.setTextWithKey(textInfo, keyInfo, true);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    class KeyEditChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            keyInfo = s.toString();
            spanTV.setTextWithKey(textInfo, keyInfo);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
