package com.cas.sync;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午8:24 2021/4/26
 * @version: V1.0
 * @review: 并发可见行测试：多个线程修改共享变量会造成并发问题
 */
public class kjxTest {

    // volatile能保证可见性
    private static volatile boolean flag = true;

    private static Object obj = new Object();


    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {
//                synchronized (obj) {
//
//                }
            }
        }).start();

        Thread.sleep(2000);
        new Thread(() -> {
            flag = false;
            System.out.println("线程修改了flag: false");
        }).start();

    }


}
