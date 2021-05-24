package com.cas.proxy;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午9:53 2021/3/22
 * @version: V1.0
 * @review:
 */
public class HelloProxyImpl implements HelloProxy{

    @Override
    public void say() {
        System.out.println("【3】==================逻辑处理=======");
    }
}
