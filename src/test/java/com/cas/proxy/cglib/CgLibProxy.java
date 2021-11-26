package com.cas.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/22 5:01 下午
 * @desc
 */
public class CgLibProxy implements MethodInterceptor {

    private Object targetObject;

    public static Object newProxy(Object obj) {
        CgLibProxy cgLibProxy = new CgLibProxy();
        cgLibProxy.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(cgLibProxy);
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib 代理OK");
        return method.invoke(targetObject, objects);
    }
}
