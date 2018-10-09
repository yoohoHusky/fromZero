package com.example.yooho.husky.myapplication.javanet.transfile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by haoou on 2018/7/26.
 */

public class UDPFileService {

    public static final int PORT_SERVICE = 10006;

    public static void main(String[] args) throws IOException {

        ServerSocket sSocket = new ServerSocket(PORT_SERVICE);
        Socket socket = sSocket.accept();

        // 1、建立连接
        InetAddress inAddress = socket.getInetAddress();
        String clientIp = inAddress.getHostAddress();
        int clientPort = socket.getPort();
        System.out.println(clientIp + "-" + clientPort + " : 连接成功......." );

        File file = new File("/Users/haoou/Downloads/b.zip");
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }

        // 2、获得流
        BufferedOutputStream bufWriter = new BufferedOutputStream(new FileOutputStream(file));
        BufferedInputStream bufIn = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream bufOut = new BufferedOutputStream(socket.getOutputStream());


        // 3、读取socket输入流，写入本地文件
        int ch;
        while ((ch = bufIn.read()) != -1) {
            bufWriter.write(ch);
        }

        // 4、关闭本地文件流
        bufWriter.close();

        // 5、控制台打印结果，并向socket输出流中写入结果
        System.out.println("文件接收完成，大小为 " + file.length());
        bufOut.write(("文件接收完成，大小为 " + file.length()).getBytes());
        bufOut.flush();
        socket.shutdownOutput();

        // 6、关闭socket流
        socket.close();
        sSocket.close();

    }
}
