package com.cas.collection.set;

import org.junit.Test;

import java.util.HashSet;

public class HashSetTest {

    @Test
    public void test1() {
        HashSet<String> hashSet = new HashSet<>();

        for (int i = 0; i <= 5; i ++) {
            hashSet.add(i + "");
        }
        // 实际上就是hashKey具有不重复性
        hashSet.contains("1"); //lg(1)

    }


}
