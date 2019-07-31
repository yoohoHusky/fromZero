package com.example.yooho.zerostart.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.yooho.zerostart.R;

public abstract class AnimateListAdapter {
    public abstract int getCount();
    public abstract View getItemView(Context context, int position);
    public abstract View getHeadView(Context context);
    public View getConfirmView(Context context) {
        View inflate = View.inflate(context, R.layout.view_animate_cofirm, null);
        return inflate;
    }
}
