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
        String eic = "kRX9EVF5gc1kF92nW/4YgpcXiFJ7hrEmkGn7rPudwkvrGYecABR2MQlwD9pw7nsiONcAGicRIbYeldiPHxIvT4LA4SJB51YNsjisVx4EmkcddsrcBk0lqS3EcCxQWkV5/XFnGvCntPNqH5Ttehe8wpW6tPsC4LElfhKdgwC6Gl4VLzK4NVd2a7hBtjVqT6lqdl2/z0gyMwoMZCEcCKB5pnsFjXPqR76HRggo0V5GR75rJmqyzx1/JK3WRL35qK3D0000000000000000000000000000000000000000";
        String pid = "EE4BC75E0E075B099C375209AF3B945DB6BAC6A436330608F64E861A38704127";
        System.out.println(HexConverter.hexEncode(eic.getBytes("UTF-8")));
        System.out.println(HexConverter.hexEncode(pid.getBytes("UTF-8")));
    }

}
