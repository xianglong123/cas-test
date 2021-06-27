package com.cas.binary;

import com.cas.des.DesEncTest;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午9:39 2021/5/22
 * @version: V1.0
 * @review: 二进制操作测试
 */
public class BinaryTest {

    @Test
    public void test() {
        int i = 1;
        System.out.println(i >> 2);
        System.out.println(i << 2);
    }


    /**
     * 16进制转2进制，2进制做加法运算
     */
    @Test
    public void test2() {
        String str = "04";
        String res = "02";
        Integer num = Integer.parseInt(str, 16);
        Integer num2 = Integer.parseInt(res, 16);
        System.out.println(Integer.toBinaryString(num));
        System.out.println(Integer.toBinaryString(num2));
        System.out.println(Integer.toBinaryString(num + num2));
    }

    @Test
    public void test3() {
        String str = "TSMR00000";
        System.out.println(str.startsWith("TSMR"));
    }

    @Test
    public void test4() throws Exception {
        String seid = "2100000E103003528819";
        byte[] bytes = seid.getBytes();
        byte[] q4 = new byte[16];
        System.arraycopy(bytes, 0, q4, 0, 8);
        System.arraycopy(bytes, bytes.length - 8, q4, 8, 8);
        String decrypt = DesEncTest.encrypt("101112131415161718191A1B1C1D1E1F", Arrays.toString(q4));
        System.out.println(decrypt);

//        byte[] bytes1 = encryptDESByByteBuf(q4, "101112131415161718191A1B1C1D1E1F".getBytes());
//        System.out.println(bytes1);
        // 50 49 48 48 48 48 48 69 48 51 53 50 56 56 49 57
        //src：byte源数组
        //srcPos：截取源byte数组起始位置（0位置有效）
        //dest,：byte目的数组（截取后存放的数组）
        //destPos：截取后存放的数组起始位置（0位置有效）
        //length：截取的数据长度
    }


}
