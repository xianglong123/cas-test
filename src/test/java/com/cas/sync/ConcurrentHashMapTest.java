package com.cas.sync;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午6:07 2021/5/5
 * @version: V1.0
 * @review: 通过例子查看源码
 */
public class ConcurrentHashMapTest {

    @Test
    public void test() {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("name", "xl");
        concurrentHashMap.put("age", "24");
        concurrentHashMap.get("age");
        concurrentHashMap.get("xl");
    }

}
