package com.cas.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午9:45 2021/4/28
 * @version: V1.0
 * @review: 测试LockSupport
 *
 * unpark(Thread t): 不是唤醒传入线程
 */
public class LockSupportTest {

    public static void main(String[] args) {
        test();
    }


    public static void test() {

        Runnable run = () -> {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "获得锁");
        };

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(run);
            list.add(t);
        }

//        for (Thread t : list) {
//            t.start();
//            LockSupport.unpark(t);
//        }

        list.get(0).start();
        list.get(1).start();
        list.get(2).start();
        LockSupport.unpark(list.get(1));
        LockSupport.unpark(list.get(2));
        LockSupport.unpark(list.get(0));




    }

}
