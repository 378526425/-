package com.wxmblog.base.common.enums;

public enum GenderEnum {
    MALE("男"),
    FEMALE("女");
    private String desc;

    GenderEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
