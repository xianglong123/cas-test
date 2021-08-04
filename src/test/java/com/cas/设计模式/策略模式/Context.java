package com.cas.设计模式.策略模式;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 3:34 下午
 * @desc
 */
public class Context {

    private Person person;

    public Context(Person person) {
        this.person = person;
    }

    public void opera() {
        person.eat();
    }

}
