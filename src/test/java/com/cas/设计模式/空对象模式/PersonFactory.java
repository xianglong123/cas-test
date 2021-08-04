package com.cas.设计模式.空对象模式;

import com.cas.设计模式.空对象模式.impl.NullPerson;
import com.cas.设计模式.空对象模式.impl.XiaoMing;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 5:35 下午
 * @desc
 */
public class PersonFactory {

    public static final String[] names = {"Rob", "Joe", "Julie"};

    public static Person getCustomer(String name){
        for (int i = 0; i < names.length; i++) {
            if (names[i].equalsIgnoreCase(name)){
                return new XiaoMing(name);
            }
        }
        return new NullPerson();
    }


}
