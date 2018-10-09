package com.example.yooho.zerostart.jnicode;

/**
 * Created by haoou on 2018/8/16.
 *
 * JNI使用流程：
 * 1. 创建声明文件：   定义一个JNI声明的借口文件，里面定义需要的方法（方法名，入参，返回值）
 * 2. 生成.class：    将声明文件 `javac` 生成.class文件
 * 3. 生成.h：        将.class文件 `javah` 生成.h文件（路径不重要，最后都会移植走）
 *                      生成的.h文件名称格式：包名_声明文件.h（可以改aac）
 * 4. 生成.c：         为了方便在java结构下创建个jni文件夹，将.h/.c文件都放入，从.h中拿到接口，在.c中实现
 * 5. 写mk文件：       编写Android.mk、Application.mk 文件，格式固定
 *      APP_ABI :=all          # Application.mk
 *
 *      LOCAL_PATH := $(call my-dir)    # Android.mk。  得到该文件的路径
 *      include $(CLEAR_VARS)
 *      LOCAL_MODULE    := desc         # 生成的so文件名称，前面会自动加上libXX
 *      LOCAL_SRC_FILES := JniCFile.c   # 需要编译的C文件的路径
 *      include $(BUILD_SHARED_LIBRARY) # 含义为：编译c/c++共享库（.so文件）
 *
 * 6. 执行nkd-build：  在jni目录（.mk所在目录）执行ndk-build（需要配置环境或者写全路径）
 * 7. 得到.so文件：     在main/libs 可以得到对应平台的.so文件
 * 8. 移植.so文件：     根据工程结构需要，移植.so文件到指定的目录下
 *
 *
 *
 */

public class JniProxy {
    static {
        System.loadLibrary("desc");
    }

    public static native String getNativeString(String s);
}
