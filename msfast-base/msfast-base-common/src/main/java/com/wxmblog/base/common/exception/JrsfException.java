/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.wxmblog.base.common.exception;

import com.wxmblog.base.common.interfaces.BaseExceptionEnumInterface;

/**
 * 自定义异常
 */
public class JrsfException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private Object data;

    public JrsfException(BaseExceptionEnumInterface exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public JrsfException setData(Object data) {
        this.data = data;
        return this;
    }

    public JrsfException setMsg(String msg) {
        this.message = msg;
        return this;
    }

    public Object getData() {
        return data;
    }
}
