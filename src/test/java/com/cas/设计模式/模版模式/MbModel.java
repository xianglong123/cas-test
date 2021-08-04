package com.cas.设计模式.模版模式;

import com.cas.设计模式.模版模式.Impl.XiaoHong;
import com.cas.设计模式.模版模式.Impl.XiaoMing;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/4 2:40 下午
 * @desc 模版模式：一个抽象类公开定义了执行它的方法的方式/模板。它的子类可以按需要重写方法实现，但调用将以抽象类中定义的方式进行。这种类型的设计模式属于行为型模式。
 */
public class MbModel {

    public static void main(String[] args) {
        Person xm = new XiaoMing();
        xm.opera();

        xm = new XiaoHong();
        xm.opera();
    }


}
