package com.cas.encryption.sm4;

import com.cas.util.HexConverter;

import java.io.UnsupportedEncodingException;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/7 3:44 下午
 * @desc 对称加密SM4
 */
public class HutoolSm4MobileTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 数据转换
        test1();
    }

    private static void test1() throws UnsupportedEncodingException {
        String eic = "C0ZJdGevyfhOeFZeZZhVSCJ8OwsfSlypkxgdwwEnIUGlMDAwMDExMDUyMDIzMDMxNzIwMjMwOTE3MQEAAAEBqRoxE6aF0f/n70R/gqnmT1qb2pJksUeRRP8ASbxDsQ5zQ4O314uFweC2jBV4aX6tbPjzGTJRv8nLvv1Ddx7xlxzO++jB7fBiznHucJfvsTE4MTJERjIxMEQCIFq6vw6bEskgvwMlWVpgmYZfpJCyvifz1x1hFbC7s9DcAiBI7s5WENX5+NjJxrovTwgQCH8tpQmwm0HgEpmyKwcXWQAA";
        String pid = "BD087A178B8056D79F6FDB42FC64A940F80975F248F663254AD7A30B387335D0";
        System.out.println(HexConverter.hexEncode(eic.getBytes("UTF-8")));
        System.out.println(HexConverter.hexEncode(pid.getBytes("UTF-8")));
    }

}
