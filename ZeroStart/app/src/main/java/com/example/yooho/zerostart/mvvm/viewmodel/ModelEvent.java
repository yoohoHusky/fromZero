package com.example.yooho.zerostart.mvvm.viewmodel;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.FocusFinder;
import android.view.WindowId;
import android.widget.Toast;

/**
 * Created by haoou on 2018/2/22.
 */

public class ModelEvent {

    public TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.e("SS", "beforeTextChanged    "
                    + (TextUtils.isEmpty(charSequence) ? "" : charSequence.toString())
            );
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.e("SS", "onTextChanged    "
                    + (TextUtils.isEmpty(charSequence) ? "" : charSequence.toString())
            );
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.e("SS", "afterTextChanged    " + editable.toString());
        }
    };

}
