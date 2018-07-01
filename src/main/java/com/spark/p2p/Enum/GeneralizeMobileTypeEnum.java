package com.spark.p2p.Enum;

/**
 * @Author: crazy
 * @Date: Created in 16:38 2018/1/5
 */
public enum GeneralizeMobileTypeEnum {
    ANDORID("1","安卓"),
    IOS("2","苹果")
    ;
    private String type;
    private String msg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    GeneralizeMobileTypeEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
