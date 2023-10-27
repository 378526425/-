package com.wxmblog.base.pay.service.impl;

import com.wxmblog.base.pay.common.rest.request.OrderSubmitRequest;
import com.wxmblog.base.pay.service.IWxPayService;
import com.wxmblog.base.pay.common.rest.response.NotifyUrlData;
import com.wxmblog.base.pay.common.rest.response.PayOrderData;

public class IWxPayServiceImpl <T extends OrderSubmitRequest> implements IWxPayService<T> {
    @Override
    public PayOrderData wxAppletPay(T request) {
        return null;
    }

    @Override
    public void appletNotifyUrl(NotifyUrlData request) {

    }

    @Override
    public PayOrderData wxPublicPay(T request) {
        return null;
    }

    @Override
    public void publicNotifyUrl(NotifyUrlData request) {

    }
}
