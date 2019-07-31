package com.example.yooho.zerostart.tools;

public class MyMathUtil {

    /**
     * 通过开始阈值，结束阈值，将得到的value换算成，自己的百分比
     * @param getValue          得到的当前客观的进度数值
     * @param startValue        自己主观的开始阈值
     * @param endValue          自己主观的结束阈值
     * @return                  自己主观的进度百分比（0~1）
     */
    public static float parseSelfProcess(float getValue, float startValue, float endValue) {
        if (startValue >= endValue) return 1.0f;

        if (getValue <= startValue) return 0.0f;
        if (getValue >= endValue) return 1.0f;
        return (getValue - startValue) / (endValue - startValue);
    }
}
