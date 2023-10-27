package com.wxmblog.base.common.enums;

public enum FrUserStatusEnum {
    ENABLE("启用"),
    DISABLE("禁用"),
    LOGOFF("注销");
    private String desc;

    FrUserStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
