package com.cas.io.fileIO.O;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author xianglong
 *
 * 文件输出流
 */
public class FileOutputStreamTest {

    private static final String path = "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/io/fileIO/O/out.txt";
    private static final String path2 = "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/io/fileIO/O/out2.txt";

    public static void main(String[] args) {
//        test();
        test2();
    }

    /**
     * write() 将十进制输出到文件
     */
    private static void test() {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            String str = "output2";
            for (int i = 0; i < str.length(); i++) {
                int b = (int) str.charAt(i);
                // 这里的int其实是ACSII转码得到的，write会将int十进制转成字符输出到文件中
                fos.write(b);
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write(byte[] )
     */
    private static void test2() {
        File file = new File(path2);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("output3".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
