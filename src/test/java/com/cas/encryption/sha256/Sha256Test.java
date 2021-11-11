package com.cas.encryption.sha256;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/11 11:46 上午
 * @desc sha256 能生成32个字节的数组，通常用十六进制的64位字符串表示
 */
public class Sha256Test {

    /**
     * sha1:
     * woaini == 18f3e922a1d1a9a140efbbe894bc829eeec260d8
     * 520    == 0b6a63765cf0acb1022fc7c84ed8dcb104f221ed
     *
     * sha256:
     * woaini == f72f3302519ee6484f54a7cbbbdf07e3f92543545a3d3174ee909b23807fdc1f
     * 520    == 0b35b06a22779418f775a804f36485f7bc978071d1709ad263a68f4f18117b11
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void test() throws NoSuchAlgorithmException {
        String text = "520";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = messageDigest.digest(text.getBytes());
        System.out.println(Hex.encodeHexString(bytes));
    }

}
