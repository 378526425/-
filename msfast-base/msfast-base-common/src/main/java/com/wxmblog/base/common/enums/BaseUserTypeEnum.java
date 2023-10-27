package com.wxmblog.base.common.enums;

public enum BaseUserTypeEnum {
    SUPER_ADMIN("超级管理员"),
    TOURIST("游客");
    private String desc;

    BaseUserTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
