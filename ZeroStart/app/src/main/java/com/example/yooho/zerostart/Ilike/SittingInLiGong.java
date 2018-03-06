package com.example.yooho.zerostart.Ilike;

/**
 * Created by haoou on 2018/3/4.
 */

public class SittingInLiGong {

    private static SittingInLiGong mInst;
    private SittingInLiGong() {}

    public static SittingInLiGong getInst() {
        if (mInst == null) {
            synchronized (SittingInLiGong.class) {
                if (mInst == null) {
                    mInst = new SittingInLiGong();
                }
            }
        }
        return mInst;
    }

    public static String getLocation() {
        return "I'm sitting in Room 207, JianSan JianHu of LiGongDa";
    }

    public static String getWhatWantSay() {
        return "I love the life in Ligong, I won't forget the time inLiGong whit somebody";
    }

    public static String getHappy() {
        return "I'm working by coding in the room where i studied for postgraduate school with her";
    }

    public static String getFear() {
        return "I will play a game on the macBook Pro, so i'm scared that i'll be criticized";
    }

}
