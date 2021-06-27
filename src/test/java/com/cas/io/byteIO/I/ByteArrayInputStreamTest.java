package com.cas.io.byteIO.I;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteArrayInputStreamTest {

    public static void main(String[] args) {
        User user = new User("xl", "24");
        final byte[] serialize = serialize(user);
        final User u = (User) deserialize(serialize);
        System.out.println(u.toString());
    }


    /**
     * ByteArrayInputStream + ObjectInputStream
     * 反序列化
     * @param bytes
     * @return
     */
    private static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);) {
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 序列化
     * @param o
     * @return
     */
    private static byte[] serialize(Object o) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);) {
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
