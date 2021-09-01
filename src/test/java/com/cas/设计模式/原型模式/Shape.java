package com.cas.设计模式.原型模式;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/1 7:03 下午
 * @desc
 */
public abstract class Shape implements Cloneable{

    private String id;
    protected String type;

    public abstract void draw();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
