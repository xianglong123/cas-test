package com.cas.jol;

import lombok.Data;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午7:57 2021/4/26
 * @version: V1.0
 * @review: 测试对象头信息
 */
@Data
public class User {

    boolean flag = false;

    String name = "xl";

    String val;

    public User() {}

    public User(boolean flag, String name, String val) {
        this.flag = flag;
        this.name = name;
        this.val = val;
    }

    public String getVal() {
        return null;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
