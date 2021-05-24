package com.cas.sync;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午8:49 2021/4/28
 * @version: V1.0
 * @review: 测试可重入锁，第一个线程未结束，第二个线程会阻塞
 * 结果：除了获得锁的线程，其他线程进入阻塞状态，等待唤醒。
 */
public class ReentrantLockTest2 {


    public static void main(String[] args) throws InterruptedException {
        test();
    }

    public static void test() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Runnable run = () -> {
            // 非获得线程会在这里阻塞，直到本线程调用unlock释放锁，底层用的unpark
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " 释放锁");
            }
        };

        Thread t = new Thread(run, "线程1");
        t.start();

        Thread t2 = new Thread(run, "线程2");
        t2.start();


        Thread t3 = new Thread(run, "线程2");
        t3.start();
    }


}
