package com.wxmblog.base.file.common.enums;

public enum FileStatusEnum {
    TEMP("临时文件"),
    SAVED("已保存的文件");
    private String desc;

    FileStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
