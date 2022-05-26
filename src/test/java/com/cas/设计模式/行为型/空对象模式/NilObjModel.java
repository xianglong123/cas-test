package com.cas.设计模式.行为型.空对象模式;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 3:42 下午
 * @desc 空对象模式：提供一种默认的非预期的处理方式
 */
public class NilObjModel {

    public static void main(String[] args) {
        Person person = PersonFactory.getCustomer("Rob");
        System.out.println(person.getName());

        Person per = PersonFactory.getCustomer("Rob2");
        System.out.println(per.getName());

    }

}
