package com.example.yooho.husky.myapplication.reflect;

/**
 * Created by haoou on 2018/7/27.
 */

public class NetCard implements MainBord.PCI{

    @Override
    public void pciOpen() {
        System.out.println("net open");
    }

    @Override
    public void pciClose() {
        System.out.println("net close");
    }
}
