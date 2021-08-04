package com.cas.设计模式.策略模式.impl;

import com.cas.设计模式.策略模式.Person;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 3:33 下午
 * @desc
 */
public class XiaoMing implements Person {

    @Override
    public void eat() {
        System.out.println("小明吃了个大包子");
    }
}
