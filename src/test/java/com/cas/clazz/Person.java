package com.cas.clazz;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/16 5:25 下午
 * @desc
 */
public class Person {

    private String name;
    private Integer age;
    private Integer height;
    private boolean isFlag;

    public Person() {}

    public Person(String name, Integer age, Integer height, boolean isFlag) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.isFlag = isFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public String like(String name) {
        String x = "我喜欢你" + name;
        System.out.println(x);
        return x;
    }
}
