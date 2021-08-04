package com.cas.设计模式.模版模式.up;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 10:21 下午
 * @desc 早上起床
 */
public class DayToUp {
    
    // xxx 在 几点钟 做了什么事情
    private String name;
    
    private String date;
    
    private String work;

    public DayToUp(Supplier<String> name, Supplier<String> date, Supplier<String> work) {
        this.name = name.get();
        this.date = date.get();
        this.work = work.get();
    }

    public void opera() {
        System.out.println(name + "在" + date + work);
    }
}
