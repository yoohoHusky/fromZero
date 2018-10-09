package com.example.yooho.husky.myapplication.javanet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by haoou on 2018/7/24.
 */

public class UDPService {
    public static final int SERVICE_PORT = 10003;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(SERVICE_PORT);
        Socket socket = serverSocket.accept();

        InetAddress address = socket.getInetAddress();
        String hostAddress = address.getHostAddress();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(hostAddress + "  :  " + line);
            out.println(line.toUpperCase());
        }

        socket.close();
        serverSocket.close();
    }
}
