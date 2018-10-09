package com.example.yooho.husky.myapplication.javanet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by haoou on 2018/7/24.
 */

public class UDPClient {
    public static final int SERTVICE_PORT = 10003;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), SERTVICE_PORT);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in));

        String inLine = null;
        System.out.println("客服端已经就绪");
        while ((inLine = bufRead.readLine()) != null) {
            System.out.println("客服端读取到录入");
            System.out.println(in.readLine());
            System.out.println("客服端读取到服务端返回数据");
            out.println(inLine);
            if ("886".equals(inLine)) {
                break;
            }
            System.out.println(in.readLine());


        }

        bufRead.close();
        socket.close();
    }
}
