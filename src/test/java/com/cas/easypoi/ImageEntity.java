package com.cas.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/12/1 2:02 上午
 * @desc
 */
public class ImageEntity implements Serializable {

    @Excel(name = "数据1")
    private Integer sj1;
    @Excel(name = "数据2")
    private Integer sj2;
    @Excel(name = "数据3")
    private Integer sj3;

    public ImageEntity(Integer sj1, Integer sj2, Integer sj3) {
        this.sj1 = sj1;
        this.sj2 = sj2;
        this.sj3 = sj3;
    }

    public Integer getSj1() {
        return sj1;
    }

    public void setSj1(Integer sj1) {
        this.sj1 = sj1;
    }

    public Integer getSj2() {
        return sj2;
    }

    public void setSj2(Integer sj2) {
        this.sj2 = sj2;
    }

    public Integer getSj3() {
        return sj3;
    }

    public void setSj3(Integer sj3) {
        this.sj3 = sj3;
    }
}
