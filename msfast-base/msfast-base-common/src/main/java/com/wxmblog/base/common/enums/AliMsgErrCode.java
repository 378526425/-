package com.wxmblog.base.common.enums;

public enum AliMsgErrCode {
    非法手机号("isv.MOBILE_NUMBER_ILLEGAL"),
    触发日发送限额("isv.DAY_LIMIT_CONTROL"),
    业务停机("isv.OUT_OF_SERVICE"),
    业务限流("isv.BUSINESS_LIMIT_CONTROL"),
    源IP地址所在的地区被禁用("isv.DENY_IP_RANGE");
    private String msg;

    AliMsgErrCode(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
