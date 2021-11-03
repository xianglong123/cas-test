package com.cas.设计模式.原型模式.impl;

import java.util.*;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/10/8 9:56 上午
 * @desc
 */
public class a {


    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> yuan = new HashMap<>();
        yuan.put("type", 2);
        yuan.put("code", "c");
        Map<String, Object> jia = new HashMap<>();
        jia.put("type", 1);
        jia.put("code", "b");
        Map<String, Object> min = new HashMap<>();
        min.put("type", 3);
        min.put("code", "a");


        list.add(min);//6 a
        list.add(jia);//1 b
        list.add(yuan);//7 c


        System.out.println(list);
        list.sort((o1, o2) -> {
            System.out.println("o1=" + o1 + " || o2=" + o2);
            return o1.get("type").toString().compareTo(o2.get("type").toString());
        });
        System.out.println("最后显示的list="+list);
    }

}
