package com.wxmblog.base.common.rest.request.sms;

import lombok.Data;

@Data
public class SmsData {

    private String name;

    private String value;

    public SmsData(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
