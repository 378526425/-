package com.wxmblog.base.pay.common.rest.response;

import lombok.Data;

@Data
public class PayDataResponse {

    private String provider;

    private String timeStamp;

    private String nonceStr;

    private String packageName;

    private String signType;

    private String paySign;
}
