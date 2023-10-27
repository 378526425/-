package com.wxmblog.base.websocket.common.enums;

public enum MessageTypeEnum {
    CONNECT("用户连接"),
    IM_MESSAGE("im消息"),
    ALIVE("心跳"),
    ANSWER("应答");
    private String desc;

    MessageTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
