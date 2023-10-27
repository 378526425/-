package com.wxmblog.base.auth.common.enums;

public enum MessageType {

    LOGIN("登陆"),
    REGISTER("注册"),
    RESETPWD("重置密码")
    ;
    private String desc;

    MessageType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
