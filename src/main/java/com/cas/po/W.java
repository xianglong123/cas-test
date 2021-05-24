package com.cas.po;

import java.util.List;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午6:17 2021/5/19
 * @version: V1.0
 * @review:
 */
public class W {
    private String code;
    private String errMsg;

    private List<Z> data;

    public List<Z> getData() {
        return data;
    }

    public void setData(List<Z> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
