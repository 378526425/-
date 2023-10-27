package com.wxmblog.base.auth.authority.service;

import com.wxmblog.base.auth.common.rest.request.wx.WxTemplateMsgRequest;
import com.wxmblog.base.auth.common.rest.response.WxAppletOpenResponse;

public interface WxAppletService {

    WxAppletOpenResponse getOpenIdInfoByCode(String code);

    String getAccessToken();

    void sendWxTemplateMessage(WxTemplateMsgRequest request);
}
