package com.example.yooho.zerostart.ui.interf;

public abstract class AnimateListObserver {
    public void childChecked(int position) {}
    public void chiildUnchecked(int position) {}
    public abstract void confirmCheck(int position);
}
