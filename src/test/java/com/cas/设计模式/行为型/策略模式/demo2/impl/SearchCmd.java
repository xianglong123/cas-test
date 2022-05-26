package com.cas.设计模式.行为型.策略模式.demo2.impl;

import com.cas.设计模式.行为型.策略模式.demo2.SystemCommand;
import com.cas.设计模式.行为型.策略模式.demo2.constants.CmdFunctionConstants;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/2 10:56 下午
 * @desc
 */
@Service
public class SearchCmd implements SystemCommand {

    @Override
    public void process() {
        System.out.println("处理 查询所有 命令！");
    }

    @Override
    public Set<String> supportFunctions() {
        HashSet<String> set = new HashSet<>();
        // 支持 关闭
        set.add(CmdFunctionConstants.SEARCH);
        return set;
    }

}
