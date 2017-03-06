####### 两种Service
1. startService,stopService
  1. 过程
    1. onCreate()
    2. onStartCommand()(onStart)
    3. onDestory()
  2. 关系：
    1. start后和吊起者之间再无关系

  3. 引用关系：
    1. start不能调用servicce里面的方法

2. bindService（）
  1. 过程
    1. onCreate
    2. onBind（）
    3. onUnbind（）
    4. onDestory（）
  2. 生命周期：
    1. 调用者挂掉，service也挂掉
  3. 引用关系
    1. 调用者可以调用里面的方法


####### handler机制
1. 背景：
  Android启动时会创建一个主线程，负责与UI组件交互，分发交互事件，因此安卓主线程也称为UI线程
2. 单线程要准守的两个规则：
  1. 不能阻塞UI线程
  2. 不能再UI线程之外访问UI组件。线程安全
3. 体系：
  1. Looper，messageQueue，handler
  每个activity会保存一个looper实例，该实例会保存一个mesageQueue对象
  looper无限循环，不断从messageQueue读出消息
  handler是一个分发者，message是一个消息体


######## AsyncTask
1. 创建时定义三种泛型：params，Progress，Result
2. 4个过程：
  1. preExecute
  2. doInbackGround（Paramas）
  3. onPreogressUpdate（Progress）
  4. onPostExecute（Result）
3. 要求：
  1. AsyncTask在UI 线程中创建
  2. execute在UI线程中调用
  3. asyncTask只能被执行一次


######## AsyncQueryHandler
1. 应用场景：  主要应用场景是操作contentProvider
2. 本质：     是handler的一个子类
3. 主要作用：
  1. 重写：onInsert/Delete/Update/QueryComplete
  2. 调用：startInsert/Delete


######网络访问
2. HttpUrlConnection，多用途，轻量级http访问类，可实现简单的网络数据访问，通过URL类得到，可以设置方法，拿到返回数据（sun）
3. HttpClient，可以设置头条，post参数，自动维护session（apache）



###### xml和html的区别
1. 本质：HTML主要用于控制数据的显示和外观      超文本标记语言
        XML用于标记数据本身的结构、数据类型    扩展标记语言
2. 语法要求，XML要个要求配套，遵循DTD结构（Document Type Definition）



####### TCP和UDP
1. TCP（传输控制协议），必须先建立连接，三次握手
2. UDP（用户数据报协议）


##### AIDL：解决进程间通信方案（Android Interface Definition Language）
1. Stub集成Bind，Binder作用：一个类集继承了binder，那么它的成员变量就可以被远程进程使用
2. 步骤：
  1. 写.aidl文件
  2. IDE自动在gen目录下生成.java文件
  3. 获得对方的.java类对象，在接口方法中，获得Stub对象


##### NDK（native develop kit）：解决android与C（.so文件）代码间通信
JNI：java native interface，
1. 过程：
  1. 创建一个java类：
    ```java
    public class TextJNI
    {
    static {
        System.loadLibrary("jniinterface");
    }
    public static native int getInt();
    public static native String  getString();
    ```
  2. 生成C++中的.h 文件，并复制到工程JNI文件夹下（com_example_textjni_TextJNI.h）
  3. 开始写C++代码
  ```c++
  #include"com_example_textjni_TextJNI.h"
  #include <stdio.h>
  #include <stdlib.h>

  int sum ()
  {
    int x,y;
    x = 100 ;
    y = 1000;
    x += y;
    return x;                              
  }//实现 com_example_textjni_textJNI.h 的方法
  JNIEXPORT jint JNICALL Java_com_example_textjni_TextJNI_getInt
    (JNIEnv *, jclass)
    {                                        
    return sum();
  }
  JNIEXPORT jstring JNICALL Java_com_example_textjni_TextJNI_getString
    (JNIEnv *env, jclass)
    {
    return env->NewStringUTF("HelloNDK");
  }
  ```
  4. 写android.mk 文件
  ```mk
  LOCAL_PATH := $(call my-dir)
  include $(CLEAR_VARS)
  LOCAL_MODULE    := jniinterface//so.文件名
  LOCAL_SRC_FILES := com_example_textjni_TextJNI.cpp//C++类名
  include $(BUILD_SHARED_LIBRARY)
  ```
  5. 生成.so文件（命令行）


###### XML的属性与元素
1. 属性：<尖括号之间的>（用于标识）
2. 元素：<>元素</>    （是组成部分）



######  web Services
1. 定义：
  1. Web servers:(Web服务)是一个用于支持网络间不同机器互操作的软件系统，它是一种自包含、自描述和模块化的应用程序，它可以在网络中被描述、发布和调用，可以将它看作是基于网络的、分布式的模块化组件。
  2. SOAP(Simple Object Access Protocol，简单对象访问协议): 是一种轻量级的、简单的、基于XML的协议，被设计用于在分布式环境 中交换格式化和固化信息的简单协议。
  3. WSDL(Web Services Description Language，即Web服务描述语言): 是一种用来描述Web服务的XML语言，它描述了Web服务的 功能、接口、参数、返回值等，便于用户绑定和调用服务
2. 工具：
  1. 安卓端：Ksoap2
3. 使用过程：
  1. soap方法                得到SoapObject，构造方法（命名空间，调用方法名）
  2. soap加参数              对soapObj设置参数值，addProperty（keyName，value）
  3. soap请求体              得到Soap的请求体，SoapSerializationEnelope对象，构造方法（SOAP协议号），并将soap方法绑定到soap请求体上
  4. 创建管理对象             创建整个过程的管理对象，构造函数（WSDL网络地址）
  5. 管理对象调用soap请求体：  调用请求
  6. 在请求体中读结果          请求体调用getResponse方法
  ```java
  // 1. 得到soap的请求方法
  SoapObject request =new SoapObject(http://service,”getName”);
  // 2. 为请求方法增加参数
  Request.addProperty(“param1”,”value”);
  Request.addProperty(“param2”,”value”);
  // 3. 得到soap请求对象
  SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
  Envelope.bodyOut = request;
  // 4. 得到管理者（调用者）
  HttpTransportSE ht = new HttpTransportSE(“http://192.168.18.17:80 /axis2/service/SearchNewsService?wsdl”);
  // 5. 管理者调用请求方法
  ht.call（null，envelope）
  // 6. 从请求方法对象中得到返回值
  SoapObject soapObject =( SoapObject) envelope.getResponse();
  ```



  
