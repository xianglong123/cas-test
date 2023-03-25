package com.cas.bo;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2023/3/24 4:15 下午
 * @desc
 */
public class Qiqihar {

    private String name;

    private String idCard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "Qiqihar{" +
                "name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}
