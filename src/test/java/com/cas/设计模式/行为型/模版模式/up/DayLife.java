package com.cas.设计模式.行为型.模版模式.up;

import com.cas.设计模式.行为型.模版模式.up.function.Processer;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 10:10 下午
 * @desc 日常生活
 */
public class DayLife {

    private final Processer getup;

    private final Processer eat;

    private final Processer sleep;

    public DayLife(Processer getup, Processer eat, Processer sleep) {
        this.getup = getup;
        this.eat = eat;
        this.sleep = sleep;
    }

    public void opera() {
        getup.process();
        eat.process();
        sleep.process();
    }
}
