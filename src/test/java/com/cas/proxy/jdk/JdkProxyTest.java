package com.cas.proxy.jdk;

import com.cas.proxy.HelloProxy;
import com.cas.proxy.HelloProxyImpl;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/22 4:13 下午
 * @desc
 */
public class JdkProxyTest {

    public static void main(String[] args) {
        HelloProxy proxy = (HelloProxy) JdkProxy.newProxy(new HelloProxyImpl());
        proxy.say();
    }

}
