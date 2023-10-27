package com.wxmblog.base.websocket.common.enums;

public enum MessageFormatEnum {

    image("图片"),
    text("文字"),
    emote("表情"),
    voice("语音"),
    video("视频");
    private String desc;

    MessageFormatEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
