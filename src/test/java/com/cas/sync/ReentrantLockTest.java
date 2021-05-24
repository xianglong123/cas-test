package com.cas.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午11:18 2021/4/28
 * @version: V1.0
 * @review: 研究下通过 ReentrantLock 替代 synchronized ,两个都是可重入锁，但是 synchronized 是关键字，一个是类
 */
public class ReentrantLockTest {

    private static int count = 0;

    public static void main(String[] args) {
        test();
    }


    public static void test() {
        Lock lock = new ReentrantLock();

        Runnable run = () -> {
            lock.lock();
            try {
                for (int i = 0; i < 10000; i ++)
                    count ++;
            } finally {
                lock.unlock();
            }
        };


        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            Thread t = new Thread(run);
            t.getState();
            list.add(t);
            t.start();
        }


        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(count);
    }

}
