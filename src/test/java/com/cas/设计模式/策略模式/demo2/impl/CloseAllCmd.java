package com.cas.设计模式.策略模式.demo2.impl;

import com.cas.设计模式.策略模式.demo2.SystemCommand;
import com.cas.设计模式.策略模式.demo2.constants.CmdFunctionConstants;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CloseAllCmd implements SystemCommand {
    @Override
    public void process() {
        System.out.println("处理 关闭所有 命令！");
    }

    @Override
    public Set<String> supportFunctions() {
        HashSet<String> set = new HashSet<>();
        // 支持 关闭
        set.add(CmdFunctionConstants.SHUT_DOWN);
        // 支持关闭窗口
        set.add(CmdFunctionConstants.CLOSE_WINDOW);
        return set;
    }

}