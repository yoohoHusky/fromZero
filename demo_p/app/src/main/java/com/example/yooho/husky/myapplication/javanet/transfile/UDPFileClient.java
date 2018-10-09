package com.example.yooho.husky.myapplication.javanet.transfile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by haoou on 2018/7/26.
 */

public class UDPFileClient {

    public static final String ADDRESS_IP = "127.0.0.1";
    public static final int PORT_CLIENT = 10005;
    public static final int PORT_SERVICE = 10006;

    public static void main(String[] args) throws Exception {

        File file = new File("/Users/haoou/Downloads/a.zip");
        if (!file.exists()) {
            System.out.println("file not exists");
            return;
        }

        // 1、建立连接
        InetAddress addressIp = InetAddress.getByName(ADDRESS_IP);
        Socket socket = new Socket(addressIp, PORT_SERVICE, addressIp, PORT_CLIENT);
        System.out.println("建立连接成功。。。");


        // 2、获得流
        BufferedInputStream bufRead = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bufOut = new BufferedOutputStream(socket.getOutputStream());
        BufferedReader bufReaderIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // 3、读取本地文件流，写入socket输出流
        int ch;
        while ((ch = bufRead.read()) != -1) {
            bufOut.write(ch);
        }
        bufOut.flush();
        socket.shutdownOutput();

        // 4、读取socket输入流，打印到控制台
        String line;
        while ((line = bufReaderIn.readLine()) != null) {
            System.out.println(line);
        }

        // 5、关本地文件流，关socket流
        socket.close();
        bufRead.close();
    }

    class A {
        @Override
        public String toString() {
            return super.toString();
        }
    }
}
