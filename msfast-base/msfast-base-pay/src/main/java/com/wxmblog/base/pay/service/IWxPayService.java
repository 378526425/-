package com.wxmblog.base.pay.service;

import com.wxmblog.base.pay.common.rest.request.OrderSubmitRequest;
import com.wxmblog.base.pay.common.rest.response.NotifyUrlData;
import com.wxmblog.base.pay.common.rest.response.PayOrderData;

public interface IWxPayService<T extends OrderSubmitRequest> {

    PayOrderData wxAppletPay(T request);

    void appletNotifyUrl(NotifyUrlData request);

    PayOrderData wxPublicPay(T request);

    void publicNotifyUrl(NotifyUrlData request);
}
