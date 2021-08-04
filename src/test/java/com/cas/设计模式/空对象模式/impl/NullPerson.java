package com.cas.设计模式.空对象模式.impl;

import com.cas.设计模式.空对象模式.Person;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 5:31 下午
 * @desc
 */
public class NullPerson extends Person {

    @Override
    public boolean isNil() {
        return true;
    }

    @Override
    public String getName() {
        return "Not Available in Customer Database";
    }
}
