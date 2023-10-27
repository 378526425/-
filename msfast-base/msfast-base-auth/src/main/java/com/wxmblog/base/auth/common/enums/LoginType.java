package com.wxmblog.base.auth.common.enums;


public enum LoginType {
    Number_Password("账号密码登录"),
    WX_Applet("微信小程序");
    private String desc;

    LoginType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
