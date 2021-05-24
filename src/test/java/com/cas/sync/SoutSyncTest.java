package com.cas.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午10:59 2021/4/28
 * @version: V1.0
 * @review:
 * 我们知道sout的输出方法加了 synchronized 关键字，我们用sout输出多线程不安全的非原子操作，结果会是安全的吗?
 *
 * 答案： sout内部的 synchronized 关键字会使本不安全的多线程操作结果安全，因为 同步代码块关键字
 *
 */
public class SoutSyncTest {

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        Runnable run = () -> {
            for (int i = 0; i < 10000; i ++) {
                System.out.println(count ++);
            }
        };

        // 开启5个线程一步执行上面方法
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            Thread t = new Thread(run);
            list.add(t);
            t.start();
        }

        // 等待子线程执行完
        for (Thread t : list) {
            t.join();
        }

        System.out.println(count);

    }




}
