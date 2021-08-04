package com.cas.common;

import org.junit.Test;

import java.util.Arrays;

public class ByteTest {

    @Test
    public void test() {
        byte a1 = '8';
        byte b1 = 'b';
        System.out.println((char) ((a1 << 8) + b1));
        byte[] c1 = {'a', 'b', 'c'};
        System.out.println(Arrays.toString(c1));
    }
}
