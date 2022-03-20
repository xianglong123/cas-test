package com.cas.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/3/7 9:37 上午
 * @desc
 */
//@Component
//@ConfigurationProperties(prefix = "jmj")
public class JmjProperties {

    private List<String> ips;

    private List<Integer> weights;

    private Integer port;

    private byte index;

    public byte getIndex() {
        return index;
    }

    public void setIndex(byte index) {
        this.index = index;
    }

    public List<Integer> getWeights() {
        return weights;
    }

    public void setWeights(List<Integer> weights) {
        this.weights = weights;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
