package com.example.yooho.zerostart.ui.view.listview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by yooho on 2016/11/22.
 */
public class PinnedListView2 extends ListView  {
    private boolean mHeaderViewVisible;
    private View mHeaderView;
    private PinnedInterface2 mAdapter;
    private int mHeaderViewWidth;
    private int mHeaderViewHeight;
    private int recordState;

    public PinnedListView2(Context context) {
        super(context);
    }

    public PinnedListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (PinnedInterface2) adapter;
        mHeaderView = mAdapter.getHeadView(getFirstVisiblePosition());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mAdapter == null) {
            return;
        }
        onUpdateHeadLayout();
        if (mHeaderViewVisible) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }

    private void remeasureHeadView() {
        mHeaderView = mAdapter.getHeadView(getFirstVisiblePosition());
        requestLayout();
    }

    private void onUpdateHeadLayout() {
        if (mAdapter == null) {
            return;
        }
        int firstPos = getFirstVisiblePosition();
        int headViewState = mAdapter.getHeadViewState(firstPos);
        if (recordState != headViewState) {
            Log.e("SS", "last " + recordState + "    new " + headViewState);
            recordState = headViewState;
            remeasureHeadView();
        }

        if (mHeaderView == null) {
            return;
        }

        switch (headViewState) {
            case PinnedInterface.PINNED_HEADER_GONE:
                mHeaderViewVisible = false;
                break;
            case PinnedInterface.PINNED_HEADER_VISIBLE:
                mAdapter.updateHeadView(mHeaderView, firstPos, 1.0f);
                if (mHeaderView.getTop() != 0) {
                    mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
                }
                mHeaderViewVisible = true;
                break;
            case PinnedInterface.PINNED_HEADER_PUSHED_UP: {
                View firstView = getChildAt(0);
                int bottom = firstView.getBottom();
                int headerHeight = mHeaderView.getHeight();
                int newTop;
                float ratio;
                if (bottom < headerHeight) {
                    newTop = (bottom - headerHeight);
                    ratio = (headerHeight + newTop)*1.0f/ headerHeight;
                } else {
                    newTop = 0;
                    ratio = 1.0f;
                }
                mAdapter.updateHeadView(mHeaderView, firstPos, ratio);
                if (mHeaderView.getTop() != newTop) {
                    mHeaderView.layout(0, newTop, mHeaderViewWidth, mHeaderViewHeight + newTop);
                }
                mHeaderViewVisible = true;
                break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("SS", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e("SS", "onLayout");
        super.onLayout(changed, left, top, right, bottom);
        if (mHeaderView != null) {
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
        }
    }
}
