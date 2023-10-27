package com.wxmblog.base.common.enums;

import com.wxmblog.base.common.interfaces.BaseExceptionEnumInterface;

public enum BaseUserExceptionEnum implements BaseExceptionEnumInterface {

    USER_EXIST_EXCEPTION(11001, "用户已存在"),
    USER_NOT_EXIST_EXCEPTION(11002, "用户不存在"),
    AGE_NOT_RANGE_EXCEPTION(11003, "年龄不在符合范围"),
    USER_STATUS_ERROR_EXCEPTION(11004, "用户状态异常"),
    PASSWORD_ERROR_EXCEPTION(11005, "密码错误");

    private Integer code;
    private String msg;

    BaseUserExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
