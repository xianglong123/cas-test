package com.cas.设计模式.模版模式.Impl;

import com.cas.设计模式.模版模式.Person;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 2:53 下午
 * @desc
 */
public class XiaoHong extends Person {

    @Override
    protected void getup() {
        System.out.println("小红2点钟起床～");
    }

    @Override
    protected void eatFood() {
        System.out.println("然后啥也没吃");
    }

    @Override
    protected void sleep() {
        System.out.println("吃完就睡了");
    }
}
