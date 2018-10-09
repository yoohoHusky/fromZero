package com.example.yooho.husky.myapplication.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

/**
 * Created by haoou on 2018/7/19.
 */

public class SplitFile {

    int aa = 0;
    static final int CACHE_SIZE = 1024;
    protected static String TEMP_DIR = "tempDir";


    public static void main(String[] args) throws Exception {
        String absPath = "/Users/haoou/Downloads";

        splitFile(absPath, "timg.jpeg");
        mergeFile(new File(absPath + File.separator + TEMP_DIR));
        Properties properties = new Properties();
        properties.setProperty("name", "file");
        properties.setProperty("age", "9");
        FileOutputStream fos = new FileOutputStream(absPath + "/" + "1.properties");
        properties.store(fos, "demo properties");
        fos.close();

        Properties properties1 = new Properties();
        FileInputStream fis = new FileInputStream(absPath + "/" + "1.properties");
        properties1.load(fis);
        System.out.println(properties.getProperty("age"));
        fis.close();
    }

    private static void mergeFile(File dirFile) throws Exception {
        if (!dirFile.exists()) {
            return;
        }

        File[] files = dirFile.listFiles();
        if (files.length <= 0) {
            return;
        }

        // 将碎片文件合并读入字节流
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new MyFileComparator());


        ArrayList<FileInputStream> inputStreams = new ArrayList<>();
        for (File file: fileList) {
//            System.out.println(file.getName());
            inputStreams.add(new FileInputStream(file));
        }
        SequenceInputStream sis = new SequenceInputStream(Collections.enumeration(inputStreams));
        FileOutputStream fos = new FileOutputStream(new File(dirFile, "copy.jpeg"));

        byte[] bytes = new byte[CACHE_SIZE];
        int len;
        while ((len = sis.read(bytes)) > 0) {
            fos.write(bytes, 0 ,len);
        }

        fos.close();
        sis.close();

    }


    private static void splitFile(String absPath, String fileName) throws Exception {

        //判断源文件是否存在
        File srcFile = new File(absPath + File.separator + fileName);
        if (!srcFile.exists()) {
            return;
        }

        // 读入源文件
        FileInputStream fis = new FileInputStream(srcFile);
        byte[] bytes = new byte[CACHE_SIZE];

        // 检查dir目录是否存在，不存在则创建
        String absDirPath = absPath + File.separator + TEMP_DIR;
        File dir = new File(absDirPath);

        if (!dir.exists()) {
            dir.mkdirs();
        } else {
            removeSelf(dir);
            dir.mkdirs();
        }

        // 读取outStream，按1M大小写入4个文件
        int len;
        int count = 0;
        while ((len = fis.read(bytes)) != -1) {
            FileOutputStream fos = new FileOutputStream(new File(dir, count +".part"));
            fos.write(bytes, 0, len);
            fos.close();
            count++;
        }

        // 关流
        fis.close();
    }

    private static void removeSelf(File dirFile) {
        File[] files = dirFile.listFiles();
        for (File file: files) {
            if (file.isDirectory()) {
                removeSelf(file);
            } else  {
                file.delete();
            }
        }
    }

    private static class MyFileComparator implements Comparator<File> {

        @Override
        public int compare(File file, File t1) {
            if (!file.getName().contains("\\.")) {
                return -1;
            }
            if (!t1.getName().contains("\\.")) {
                return 1;
            }

            String name0 = file.getName();
            String name1 = t1.getName();

            int f0 = Integer.parseInt(name0.split("\\.")[0]);
            int f1 = Integer.parseInt(name1.split("\\.")[0]);
            System.out.println(name0 + "   " + (f0-f1) + "  " + name1);
            return f0 -f1;
        }
    }
}

abstract class Fu {
    abstract void getA() throws Exception;

}

class Zi extends Fu {

    @Override
    void getA() throws Exception {

    }
}