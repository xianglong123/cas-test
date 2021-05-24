package com.cas.sync;

import java.util.concurrent.locks.Lock;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午8:20 2021/4/27
 * @version: V1.0
 * @review: 研究synchronized异常为什么会释放锁
 */
public class ErrorOutLockTest {

    private static Object obj = new Object();

    public static void main(String[] args) {
        synchronized (obj) {
            System.out.println("1");
        }
    }

    public synchronized void test() {
        System.out.println("a");
    }
}
