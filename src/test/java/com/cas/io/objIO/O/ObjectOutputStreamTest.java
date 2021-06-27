package com.cas.io.objIO.O;


import com.cas.io.byteIO.I.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * author xianglong
 * ObjectOutputStream 测试
 * 对象流
 */
public class ObjectOutputStreamTest {

    private static final String path = "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/io/objIO/test.txt";

    public static void main(String[] args) {

        test();

    }

    /**
     * FileOutputStream + ObjectOutputStream 文件流+对象流
     * 保存到文件中的数据是二进制，一定会乱码。不要试图修复
     */
    private static void test() {
        try (FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(User.getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
