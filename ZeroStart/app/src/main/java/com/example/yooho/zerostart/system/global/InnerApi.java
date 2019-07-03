package com.example.yooho.zerostart.system.global;

import com.example.yooho.zerostart.system.global.core.SourceManager;

public class InnerApi {
    public static String getSkinPath(int type) {
        return SourceManager.getInstance().getSkinPath(type);
    }
}
