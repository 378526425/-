package com.wxmblog.base.pay.common.rest.response;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-05-04 14:19
 **/

@Data
public class NotifyUrlData {

    /*
     * @Description  商户订单号
     **/
    private String outTradeNo;

    /*
     * @Description  附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     **/
    private String attach;
}
