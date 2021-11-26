package com.cas.proxy.other;


import com.cas.BaseTest;
import com.cas.proxy.HelloProxy;
import com.cas.proxy.HelloProxyImpl;
import com.cas.proxy.MyInterceptor;
import com.cas.proxy.ProxyBean;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/8 2:05 下午
 * @desc
 */
class HelloProxyTest extends BaseTest {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        HelloProxy helloProxy = new HelloProxyImpl();
        HelloProxy proxy = (HelloProxy) ProxyBean.getProxyBean(helloProxy, new MyInterceptor());
        proxy.say();
    }


}