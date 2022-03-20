package com.cas.common;

import com.cas.service.Impl.WeightRandom;
import org.apache.commons.math3.util.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/3/7 2:56 下午
 * @desc
 */
//@Configuration
public class JmjConfig {
    
    @Bean
    public WeightRandom<String, Integer> weightRandom(JmjProperties jmjProperties) {
        // 权重配置校验，以ips的个数为准，权重为辅。ips.size() > weights ，权重默认为1,开发不要配错
        List<String> ips = jmjProperties.getIps();
        List<Integer> weights = jmjProperties.getWeights();
        List<Pair<String, Integer>> pairs = new ArrayList<>();
        for (int i = 0; i < ips.size(); i ++) {
            pairs.add(new Pair<>(ips.get(i), weights.get(i)));
        }
        return new WeightRandom<>(pairs);
    }
    
}
