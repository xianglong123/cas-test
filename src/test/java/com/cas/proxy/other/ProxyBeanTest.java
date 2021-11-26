package com.cas.proxy.other;

import com.cas.BaseTest;
import com.cas.proxy.HelloProxy;
import com.cas.proxy.HelloProxyImpl;
import com.cas.proxy.MyInterceptor;
import com.cas.proxy.ProxyBean;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午10:14 2021/3/22
 * @version: V1.0
 * @review:
 */
public class ProxyBeanTest extends BaseTest {

    @Test
    public void test() {
        HelloProxy helloProxy = new HelloProxyImpl();
        HelloProxy proxy = (HelloProxy) ProxyBean.getProxyBean(helloProxy, new MyInterceptor());
        proxy.say();
    }

}
