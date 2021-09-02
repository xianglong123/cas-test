package com.cas.设计模式.策略模式.demo2;

import java.util.Set;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/2 11:26 上午
 * @desc
 */
public interface SystemCommand {

    /**
     * 指令的具体处理逻辑
     */
    void process();

    /**
     * 指令的作用是什么，支持的操作有哪些？
     */
    Set<String> supportFunctions();

}
