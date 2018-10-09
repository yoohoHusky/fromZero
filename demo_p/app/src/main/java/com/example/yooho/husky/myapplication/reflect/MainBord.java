package com.example.yooho.husky.myapplication.reflect;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by haoou on 2018/7/27.
 */

public class MainBord {

    public static void main(String[] args) throws Exception {

        System.out.println("run mainBord");

        File file = new File("pci.properties");
        FileInputStream inputStream = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(inputStream);
        for (int i=1; i<= properties.size(); i++) {
            String className = properties.getProperty("pci_" + i);
            Class clazz = Class.forName(className);
            PCI pci = (PCI) clazz.newInstance();
            pci.pciOpen();
            pci.pciClose();
        }

        inputStream.close();
    }

    interface PCI {
        void pciOpen();
        void pciClose();
    }

    public class GameCard implements PCI {

        @Override
        public void pciOpen() {
            System.out.println("game open");
        }

        @Override
        public void pciClose() {
            System.out.println("game open");
        }
    }

}
