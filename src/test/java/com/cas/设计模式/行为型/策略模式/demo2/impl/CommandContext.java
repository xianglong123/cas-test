package com.cas.设计模式.行为型.策略模式.demo2.impl;

import com.cas.设计模式.行为型.策略模式.demo2.SystemCommand;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/2 10:42 下午
 * @desc
 */
@Component
public class CommandContext implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public SystemCommand getInstance(String operation) {
        Map<String, SystemCommand> beans = applicationContext.getBeansOfType(SystemCommand.class);
        Set<String> keySet = beans.keySet();
        for (String key : keySet) {
            if (beans.get(key).supportFunctions().contains(operation)) {
                return beans.get(key);
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
