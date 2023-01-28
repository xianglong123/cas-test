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
        String eic = "C5RMRDIchdgUjdwUPae/Hkvm3TJPXSafB3vhdu54XpJ1MDAwMDExMDUyMDIyMDgwODIwMjMwMjA4MQEAAAEBqRoxE6aF0f/n70RsIp2SAFTZ8Y12/E/TtAkrpYfNzY6a6rGFCR29MXc72IlVwJt5feQDsfwRQmNU30R0FEWgiJePfN+XL8Qb1T1VOS0tczA2NTc5NDBFMEUCIQCYD+L/5XK4njd3vqSlr1FmTEqvpF7FqCs3oiTgWM1f1gIgCwiygqob0ayQUf5VbIsDO7thK64q1Yh7hJtTKor+06AA";
        String pid = "C80B37362358C952F77B5646D1D842C23A41628C2701ED7D47D64FFD19DEB212";
        System.out.println(HexConverter.hexEncode(eic.getBytes("UTF-8")));
        System.out.println(HexConverter.hexEncode(pid.getBytes("UTF-8")));
    }

}
