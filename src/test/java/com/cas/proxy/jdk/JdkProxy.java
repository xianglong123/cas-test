package com.cas.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/22 4:17 下午
 * @desc
 */
public class JdkProxy implements InvocationHandler {

    private Object proxyObject;

    public static Object newProxy(Object target) {
        JdkProxy jdkProxy = new JdkProxy();
        jdkProxy.proxyObject = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), jdkProxy);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk代理生效");
        return method.invoke(proxyObject, args);
    }
}
