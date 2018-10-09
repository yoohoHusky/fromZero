package com.example.yooho.husky.myapplication.javanet;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by haoou on 2018/7/24.
 */

public class IpDemo {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();

        InetAddress[] allByName = InetAddress.getAllByName("www.baidu.com");
        for (InetAddress add : allByName) {
            System.out.println(add.getHostName());
            System.out.println(add.getHostAddress());
        }
    }
}
