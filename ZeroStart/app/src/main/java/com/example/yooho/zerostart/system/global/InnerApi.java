package com.example.yooho.zerostart.system.global;

import com.sdbean.werewolf.global.core.SourceManager;

public class InnerApi {
    public static String getSkinPath(int type) {
        return SourceManager.getInstance().getSkinPath(type);
    }
}
