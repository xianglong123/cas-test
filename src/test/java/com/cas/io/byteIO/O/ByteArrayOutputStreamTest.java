package com.cas.io.byteIO.O;


import java.io.*;

/**
 * 字节数组输出流
 */
public class ByteArrayOutputStreamTest {
    private static final String path = "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/io/fileIO/O/out.txt";

    public static void main(String[] args) {
//        test();
//        test1();
        test2();
    }

    /**
     * 简单使用
     */
    private static void test() {
        int a = 0;
        int b = 1;
        int c = 2;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bout.write(a);
        bout.write(b);
        bout.write(c);
        byte[] buff = bout.toByteArray();
        for (int i = 0; i < buff.length; i++) {
            System.out.println(buff[i]);
        }
        System.out.println("****************");
        ByteArrayInputStream bin = new ByteArrayInputStream(buff);
        while ((b = bin.read()) != -1) {
            System.out.println(b);
        }
    }


    /**
     * ByteArrayOutputStream + DataOutputStream
     */
    public static void test1() {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        String name = "xl";
        int age = 24;

        try {
            dout.writeUTF(name);
            dout.writeInt(age);
            byte[] buff = bout.toByteArray();
            ByteArrayInputStream bin = new ByteArrayInputStream(buff);
            DataInputStream dis = new DataInputStream(bin);
            String named = dis.readUTF();
            int aged = dis.readInt();
            System.out.println(named + " " + aged);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * FileInputStream + ByteArrayOutputStream
     */
    private static void test2() {
        try (FileInputStream fis = new FileInputStream(path);) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            // public int read(byte[] r) throws IOException{}
            // 这个方法从输入流读取r.length长度的字节。返回读取的字节数。如果是文件结尾则返回-1。
            while ((len = fis.read(buffer)) != -1) {
                // 将指定字节数组中从偏移量 off 开始的 len 个字节写入此字节数组输出流
                bout.write(buffer, 0, len);
            }
            byte[] data = bout.toByteArray();
            System.out.println(new String(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}
