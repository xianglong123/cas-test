package com.cas.proxy.cglib;

import com.cas.proxy.HelloProxy;
import com.cas.proxy.HelloProxyImpl;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/22 5:20 下午
 * @desc
 */
public class CgLibProxyTest {

    public static void main(String[] args) {
        HelloProxy o = (HelloProxy) CgLibProxy.newProxy(new HelloProxyImpl());
        o.say();
    }

}
