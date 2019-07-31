package com.example.yooho.zerostart.ui.bean;

import android.view.View;

public class AnimateListConfirmBean {

    private View view;
    private int centerX;

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    private int centerY;
    private int finalWidth;
    private int finalHeight;
    private int borderWidth;
    private int borderHeight;

    public AnimateListConfirmBean(int centerX, int centerY, int finalWidth, int finalHeight, float scaleRadio, View view) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.finalWidth = finalWidth;
        this.finalHeight = finalHeight;
        this.view = view;

        this.borderWidth = (int) (finalWidth * scaleRadio);
        this.borderHeight = (int) (finalHeight * scaleRadio);
    }

    public int getShowLeft(float process) {
        return centerX - getCurrentWidth(process) / 2;
    }

    public int getShowTop(float process) {
        return centerY - getCurrentHeight(process) / 2;
    }

    public int getCurrentWidth(float process) {
        if (process <=0) return 0;
        return (int) ((finalWidth - borderWidth) * process + borderWidth);
    }

    public int getCurrentHeight(float process) {
        if (process <=0) return 0;
        return (int) ((finalHeight - borderHeight) * process + borderHeight);
    }

    public View getView() {
        return view;
    }


}
