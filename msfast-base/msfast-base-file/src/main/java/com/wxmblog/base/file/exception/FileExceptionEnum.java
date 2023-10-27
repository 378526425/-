package com.wxmblog.base.file.exception;

import com.wxmblog.base.common.interfaces.BaseExceptionEnumInterface;

public enum FileExceptionEnum implements BaseExceptionEnumInterface {
    FileNameLengthLimitExceeded_Exception(11001, "文件名称超长限制异常类"),
    InvalidExtension_Exception(11002, "文件格式校验异常"),
    FileSizeLimitExceededException(11003, "超出最大大小"),
    File_Exists_Exception(11004, "文件已存在"),
    File_NOT_Exists_Exception(11005, "文件不存在");

    private Integer code;
    private String msg;


    FileExceptionEnum(Integer code, String msg) {
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
