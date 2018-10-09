package com.example.yooho.husky.myapplication.GUI;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by haoou on 2018/7/21.
 */

public class FrameDemo {

    public static void main(String[] args) {
        Frame frame = new Frame("这是一个窗口");

        frame.setBounds(200, 200, 200, 200);
        frame.setLayout(new FlowLayout());

        Button button = new Button("这是一个按钮");
        frame.add(button);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                System.out.println("click close");
            }
        });


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("button clicked");
            }
        });

        frame.setVisible(true);
        System.out.println("ok");
    }
}
