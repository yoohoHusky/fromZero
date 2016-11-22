package com.example.yooho.zerostart.ui.view.listview;

import android.view.View;

/**
 * 展示、更新一个view
 * Created by yooho on 2016/11/22.
 */
interface PinnedInterface2 {

    int PINNED_HEADER_GONE = 0;
    int PINNED_HEADER_VISIBLE = 1;
    int PINNED_HEADER_PUSHED_UP = 2;

    /**
     * 更新展示的headView
     * @param position
     * @return
     */
    View getHeadView(int position);

    /**
     * 得到一个headView的状态,用来确定是否进行压缩
     * @param position
     * @return
     */
    int getHeadViewState(int position);

    /**
     * 通知headView被压缩时如何展示
     * @param header
     * @param position 可以展示全的第一个view的index
     * @param ratio
     */
    void updateHeadView(View header, int position, float ratio);
}
