package com.cas.设计模式.策略模式.demo2;

import com.cas.设计模式.策略模式.demo2.constants.CmdFunctionConstants;
import com.cas.设计模式.策略模式.demo2.impl.CommandContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/2 10:47 下午
 * @desc
 *
 * 这里实现的策略模式好处是对若干个操作可以通过一个策略类来处理，那么就可以用这种形式
 */
public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        CommandContext commandContext= (CommandContext) applicationContext.getBean("commandContext");
        SystemCommand command = commandContext.getInstance(CmdFunctionConstants.SEARCH);
        command.process();
    }

}
