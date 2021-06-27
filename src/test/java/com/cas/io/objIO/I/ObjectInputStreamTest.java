package com.cas.io.objIO.I;

import com.cas.io.byteIO.I.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * author : xianglong
 * 文件流配合对象流
 *
 */
public class ObjectInputStreamTest {
    private static final String path = "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/io/objIO/test.txt";

    public static void main(String[] args) {
        test();
    }

    /**
     * 从文件中获取文件流，并反序列化为对象
     */
    private static void test() {
        try(FileInputStream fis = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis)) {
            User o = (User) ois.readObject();
            System.out.println(o.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
