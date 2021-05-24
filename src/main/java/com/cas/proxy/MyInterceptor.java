package com.cas.proxy;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午11:03 2021/3/22
 * @version: V1.0
 * @review:
 */
public class MyInterceptor implements Interceptor {

    @Override
    public Object around(Invocation invocation) throws Throwable {
        System.out.println("【2】==================around before ......");
        Object obj = invocation.proceed();
        System.out.println("【4】==================around after ......");
        return obj;
    }

    @Override
    public boolean useAround() {
        return true;
    }

    @Override
    public void afterThrowing() {
        System.out.println("【5】==================afterThrowing...");
    }

    @Override
    public void afterReturning() {
        System.out.println("【5】==================afterReturning...");
    }
}
