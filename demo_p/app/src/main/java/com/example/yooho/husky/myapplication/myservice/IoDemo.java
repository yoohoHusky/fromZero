package com.example.yooho.husky.myapplication.myservice;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by haoou on 2018/7/14.
 */

public class IoDemo {
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("/Users/haoou/Downloads/stu_code/android/fromZero/demo_p/app/src/main/java/com/example/yooho/husky/myapplication/io/IoDemo.java");
        FileWriter fw = new FileWriter("/Users/haoou/Downloads/copy.txt");

        char[] buf = new char[4];
        int len = 0;
        while ((len = fr.read(buf)) != -1) {
            System.out.println("write once");
            fw.write(buf, 0 ,len);
        }

        fr.close();
        fw.close();
    }
}
