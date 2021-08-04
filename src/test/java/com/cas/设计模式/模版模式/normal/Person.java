package com.cas.设计模式.模版模式.normal;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 2:46 下午
 * @desc
 */
public abstract class Person {

    /**
     * 起床
     */
    protected abstract void getup();

    /**
     * 吃饭
     */
    protected abstract void eatFood();

    /**
     * 睡觉
     */
    protected abstract void sleep();

    /**
     * 操作
     */
    public final void opera() {
        this.getup();
        this.eatFood();
        this.sleep();
    }


}
