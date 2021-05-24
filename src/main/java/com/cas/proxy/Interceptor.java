package com.cas.proxy;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午11:05 2021/3/22
 * @version: V1.0
 * @review:
 */
public interface Interceptor {

    Object around(Invocation invocation) throws InvocationTargetException, IllegalAccessException, Throwable;

    boolean useAround();

    void afterThrowing();

    void afterReturning();

}
