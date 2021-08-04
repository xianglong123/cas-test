package com.cas.设计模式.策略模式;

import com.cas.设计模式.策略模式.impl.XiaoHong;
import com.cas.设计模式.策略模式.impl.XiaoMing;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 3:32 下午
 * @desc 策略模式：一个类的行为或其算法可以在运行时更改。这种类型的设计模式属于行为型模式。
 * 在策略模式中，我们创建表示各种策略的对象和一个行为随着策略对象改变而改变的 context 对象。策略对象改变 context 对象的执行算法。
 */
public class ClModel {

    /**
     * 你看这个还是面向接口编程，中间的Context对象类似适配层。关键还是定义的对象里面动作不同导致执行结果不同
     * @param args
     */
    public static void main(String[] args) {
        Context context = new Context(new XiaoHong());
        context.opera();

        Context ct = new Context(new XiaoMing());
        ct.opera();

    }

}
