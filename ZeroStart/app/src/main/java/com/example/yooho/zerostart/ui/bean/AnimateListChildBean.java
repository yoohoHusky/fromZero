package com.example.yooho.zerostart.ui.bean;

import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.yooho.zerostart.tools.MyMathUtil;


public class AnimateListChildBean {
    private static final float TIME_ITEM_DELAY_RADIOS = 0.02f;
    private static final float TIME_LINE_DELAY_RADIOS = 0.3f;

    public int getIndex() {
        return index;
    }

    private final int index;
    private final int itemPhase;
    private final int lineHeight;
    int windowWidth;
    int col;
    int row;

    int width;
    int height;

    public View getView() {
        return view;
    }

    View view;

    private int getLocalX() {
        if (row % 2 == 0) {
            return col * width;
        } else {
            return windowWidth - col * width;
        }
    }

    private int getLocalY() {
        if (row == 0) {
            return row * lineHeight + itemPhase * (4 - col);
        } else {
            return row * lineHeight + itemPhase * col;
        }
    }


    private int getMoveStartX() {
        if (row % 2 == 0) {
            return getLocalX() - windowWidth;
        } else {
            return getLocalX() + windowWidth;
        }
    }

    private int getMoveStartY() {
        return getLocalY();
    }


    public int getShowX(float process) {
//        float showX = MyMathUtil.parseSelfProcess(process, row * 0.3f, 1 - (2 - row) * 0.3f);
        float showX = MyMathUtil.parseSelfProcess(process, col * TIME_ITEM_DELAY_RADIOS + row * TIME_LINE_DELAY_RADIOS, 1 - (4 - col) * TIME_ITEM_DELAY_RADIOS - (2 - row) * TIME_LINE_DELAY_RADIOS);
        showX = new OvershootInterpolator().getInterpolation(showX);
        return (int) ((getLocalX() - getMoveStartX()) * showX + getMoveStartX());
    }

    public int getShowY(float x) {
        return getMoveStartY();
    }

    public AnimateListChildBean(int index, int row, int col, int width, int height, View view, int windowWidth, int lineHeight, int itemPhase) {
        this.index = index;
        this.row = row;
        this.col = col;
        this.width = width;
        this.height = height;

        this.view = view;
        this.windowWidth = windowWidth;
        this.lineHeight = lineHeight;
        this.itemPhase = itemPhase;
    }

}
