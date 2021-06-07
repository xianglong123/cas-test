package com.cas.thread;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThreadTest {


    /**
     * 建立三个线程A、B、C，A线程打印10次字母A，B线程打印10次字母B,C线程打印10次字母C，但是要求三个线程同时运行，并且实现交替打印，即按照ABCABCABC的顺序打印。
     */
    @Test
    public void test() {
        for (int x = 1; x <= 10; x ++) {
            CompletableFuture.runAsync(() -> {
                System.out.println("A");
            }).thenRunAsync(() -> {
                System.out.println("B");
            }).thenRunAsync(() -> {
                System.out.println("C");
            });

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
