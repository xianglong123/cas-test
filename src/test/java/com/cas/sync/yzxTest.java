package com.cas.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午8:39 2021/4/26
 * @version: V1.0
 * @review: 原子性测试：多个线程对共享变量做操作，会造成并发问题
 * 并发变成时，会出现原子性问题，当一个线程对共享变量操作到一半时，另外的线程
 * 也有可能来操作此共享变量，干扰了前一个线程的操作。
 *
 * 扩展：为什么AtomicInteger能保证原子性
 */
public class yzxTest {

//    private static AtomicInteger number = new AtomicInteger();

    private static int number = 0;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        Runnable runnable = () -> {
          for (int i = 0; i < 10000; i ++) {
//              synchronized (yzxTest.class) {
//                number.incrementAndGet();
              number ++;
//              }
          }
        };

        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 5; i ++) {
            Thread t = new Thread(runnable);
            t.start();
            list.add(t);
        }

        // 避免主线程结束，导致子线程还没结束，用join卡住
        for (Thread t : list) {
            t.join();
        }

        System.out.println(number + "耗时 " + (System.currentTimeMillis() - start) + "ms");



    }


}
