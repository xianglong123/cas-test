package com.cas.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午9:10 2021/4/28
 * @version: V1.0
 * @review: 公平锁测试：到底公不公平
 */
public class FairSyncTest {

    public static void main(String[] args) throws InterruptedException {
        test();
    }


    public static void test() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
        Runnable run = () -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } finally {
                lock.unlock();
//                System.out.println(Thread.currentThread().getName() + " 释放锁");
            }
        };

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(run, "线程" + i);
            list.add(t);
        }

//        Thread.sleep(1000);
//        obj.notifyAll();

        for (Thread t : list) {
            t.start();
        }

//        System.out.println("over");
    }


}
