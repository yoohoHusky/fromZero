
## 使用JNI步骤：

####  一、创建.so文件
 1. 编写JNI的接口文件，载入jni模块，并声明native方法
 2. 将接口文件生成对应的class文件     （javac）
 3. 将class文件编译成   .h头文件      (javah)
 4. 在main目录下（java同级）创建jni文件夹，创建main.c文件，将.h内容写入main.c，并实现其自有方法
 5. 在local.properties下配置NDK路径，在根目录的build.gradle中编辑ndk{modelName,ldLibs,abiFilters}
 6. 编译工程，在build-intermediates-debug-lib目录下可以生成对应的.so 文件（makefile文件，androidStudio已经版帮忙生成好了）
 
 
#### 二、使用.so文件
 1. 在main目录下创建jniLibs文件夹（默认路径），导入.so文件
 2. 创建jni接口文件，加载.so名称模块，并声明native方法
 3. 在需要的位置，调用接口文件的native方法
 
 