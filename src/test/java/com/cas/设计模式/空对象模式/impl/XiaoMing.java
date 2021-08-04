package com.cas.设计模式.空对象模式.impl;

import com.cas.设计模式.空对象模式.Person;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 5:27 下午
 * @desc
 */
public class XiaoMing extends Person {

    public XiaoMing(String name) {
        this.name = name;
    }

    @Override
    public boolean isNil() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
