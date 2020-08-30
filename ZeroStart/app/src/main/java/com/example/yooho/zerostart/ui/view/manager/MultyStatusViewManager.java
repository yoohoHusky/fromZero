package com.example.yooho.zerostart.ui.view.manager;

import android.content.Context;
import android.view.View;

import com.example.yooho.zerostart.R;

import java.lang.ref.WeakReference;

public class MultyStatusViewManager {

    private final WeakReference<Context> conRef;
    private View mRootView;
    private View compressContainer;
    private View extendContainer;

    public MultyStatusViewManager(Context context) {
        conRef = new WeakReference(context);
        initView(context);
    }

    private void initView(Context context) {
        mRootView = View.inflate(context, R.layout.multy_status_view_1, null);
        compressContainer = mRootView.findViewById(R.id.compress_container);
        extendContainer = mRootView.findViewById(R.id.extend_container);
    }

    public View getRootView() {
        return mRootView;
    }

    public void updateCompressStatus() {
        compressContainer.setVisibility(View.VISIBLE);
        extendContainer.setVisibility(View.GONE);
    }

    public void updateExtendStatus() {
        compressContainer.setVisibility(View.GONE);
        extendContainer.setVisibility(View.VISIBLE);
    }
}
