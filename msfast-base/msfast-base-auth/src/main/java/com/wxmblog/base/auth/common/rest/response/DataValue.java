package com.wxmblog.base.auth.common.rest.response;

import lombok.Data;

@Data
public class DataValue {

    private Object value;

    public DataValue(Object value) {
        this.value = value;
    }
}
