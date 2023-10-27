package com.wxmblog.base.auth.common.enums;

public enum ConfigAccessEnum {

    INNER("内部"),
    PUBLIC("公开"),
    PRIVATE("私密");
    private String desc;

    ConfigAccessEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
