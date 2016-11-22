package com.example.yooho.zerostart.ui.view.listview.data;

import android.text.TextUtils;

/**
 * Created by yooho on 2016/11/22.
 */
public class Contact {
    private int id;
    private String name;
    private String pinyin;
    public String sortLetter = "#";
    public String sectionStr;
    String phoneNumber;
    public boolean isSection;

    public Contact(int id, String name) {
        this.id = id;
        this.name = name;
        this.pinyin = name.substring(0,1);
        if (!TextUtils.isEmpty(pinyin)) {
            String sortString = this.pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                this.sortLetter = sortString.toUpperCase();
            } else {
                this.sortLetter = "#";
            }
        }
    }

    @Override
    public String toString() {
        if (isSection) {
            return name;
        } else {
            //return name + " (" + sortLetter + ", " + pinyin + ")";
            return name + " (" + phoneNumber + ")";
        }
    }
}
