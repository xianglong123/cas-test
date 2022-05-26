package com.cas.设计模式.行为型.策略模式.impl;

import com.cas.设计模式.行为型.策略模式.Person;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 3:33 下午
 * @desc
 */
public class XiaoHong implements Person {

    @Override
    public void eat() {
        System.out.println("小红吃了个小包子");
    }
}
