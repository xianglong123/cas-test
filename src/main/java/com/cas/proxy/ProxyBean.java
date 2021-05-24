package com.cas.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午9:55 2021/3/22
 * @version: V1.0
 * @review:
 */
public class ProxyBean implements InvocationHandler {

    private Object target = null;

    private Interceptor interceptor = null;

    public static Object getProxyBean(Object obj, Interceptor interceptor) {
        ProxyBean proxyBean = new ProxyBean();
        proxyBean.target = obj;
        proxyBean.interceptor = interceptor;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), proxyBean);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean exceptionFlag = false;
        Object retObj = null;
        System.out.println("【1】==================动态代理start===================");
        Invocation invocation = new Invocation(target, method, args);
        try {
            if (interceptor.useAround()) {
                retObj = interceptor.around(invocation);
            } else {
                retObj = method.invoke(target, args);
            }
        } catch (Exception e) {
            // 产生异常
            exceptionFlag = true;
        }

        if(exceptionFlag) {
            this.interceptor.afterThrowing();
        } else {
            this.interceptor.afterReturning();
        }

        System.out.println("【6】==================动态代理end=====================");
        return retObj;
    }
}
