package com.cas.encryption.sm4;


public enum BaseSmEnum {

    /**
     *
     */
    ENCODING("UTF-8"),
    ALGORITHM_NAME("SM4"),
    ALGORITHM_NAME_CBC_PADDING("SM4/CBC/NoPadding"),
    ALGORITHM_NAME_ECB_NO_PADDING("SM4/ECB/NoPadding"),

    ;


    private String msg;

    BaseSmEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseEnum{" +
                "msg='" + msg + '\'' +
                '}';
    }
}

