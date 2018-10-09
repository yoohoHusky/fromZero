#include <string.h>
#include <jni.h>



jstring JNICALL Java_com_example_yooho_zerostart_jnicode_JniProxy_getNativeString
  (JNIEnv * env, jclass jclazz, jstring srcStr) {
        //定义一个C语言字符串
        char* cstr = "hello form c";
        //返回值是java字符串，所以要将C语言的字符串转换成java的字符串
        //在jni.h 中定义了字符串转换函数的函数指针
        //jstring   (*NewStringUTF)(JNIEnv*, const char*);
        //第一种方法：很少用
        jstring jstr1 = (*(*env)).NewStringUTF(env, "   return   P");
        //第二种方法，推荐
        jstring jstr2 = (*env) -> NewStringUTF(env,"   return   P");
        return jstr2;
}
