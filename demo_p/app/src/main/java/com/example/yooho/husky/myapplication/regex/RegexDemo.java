package com.example.yooho.husky.myapplication.regex;

/**
 * Created by haoou on 2018/7/27.
 */

public class RegexDemo {
    public static void main(String[] args) {
        String qq = "aob";
        String reg = "ao*b";
        boolean b = qq.matches(reg);
        System.out.println(qq + ":" + b);
    }
}
