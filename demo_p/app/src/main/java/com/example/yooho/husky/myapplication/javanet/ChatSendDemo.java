package com.example.yooho.husky.myapplication.javanet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by haoou on 2018/7/24.
 */

public class ChatSendDemo {
    public static final int SEND_PORT = 10002;
    public static final int RECEIVE_PORT = 10001;

    public static String LOCAL_IP_ADDRESS;


    public static void main(String[] args) throws UnknownHostException {
        LOCAL_IP_ADDRESS = InetAddress.getLocalHost().getHostAddress();

        new Thread(new Send()).start();

    }


    static class Send implements Runnable {

        /**
         * 发送端：
         * 1、创建DatagramaSocket，设置发送端的port
         * 2、从system.in中拿输入
         * 3、创建DatagramaPacket，设置接收端的port
         * 4、发送
         * 5、关流
         */

        @Override
        public void run() {
            DatagramSocket socket = null;
            try {
                System.out.println("....发送端启动.....");
                socket = new DatagramSocket(SEND_PORT);

                String buf;
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {

                    if ((buf = reader.readLine()) != null) {
                        byte[] bytes = buf.getBytes();
                        DatagramPacket packet = new DatagramPacket(
                                bytes, 0, bytes.length,
                                InetAddress.getLocalHost(), RECEIVE_PORT);
                        socket.send(packet);
                        if ("886".equals(buf)) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            socket.close();
        }
    }
}















