package com.wxmblog.base.pay.common.rest.response;

import lombok.Data;

@Data
public class PayOrderData {

    /*
     * @Description  商品描述 必填
     **/
    private String body;

    /*
     * @Description  商户订单号 必填
     **/
    private String outTradeNo;

    /*
     * @Description 标价金额 单位为分 必填
     **/
    private Integer totalFee;

    /*
     * @Description  附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用 非必填
     **/
    private String attach;

}
