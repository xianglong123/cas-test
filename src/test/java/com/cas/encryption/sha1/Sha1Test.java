package com.cas.encryption.sha1;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/11 9:21 上午
 * @desc sha1 能生成180位（20字节）长度散列字符
 */
public class Sha1Test {

    /**
     * woaini == 18f3e922a1d1a9a140efbbe894bc829eeec260d8
     * 520    == 0b6a63765cf0acb1022fc7c84ed8dcb104f221ed
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void test() throws NoSuchAlgorithmException {
        String text = "i love you";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] bytes = messageDigest.digest(text.getBytes());
        System.out.println(Hex.encodeHexString(bytes));
    }



}
