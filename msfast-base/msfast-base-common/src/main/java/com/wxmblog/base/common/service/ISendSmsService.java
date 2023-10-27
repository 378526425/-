package com.wxmblog.base.common.service;

import com.wxmblog.base.common.rest.request.sms.SmsData;

import java.util.List;

public interface ISendSmsService {


    /**
     * @Description: 发送短信验证码
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/23 下午4:31
     */
    void sendSms(String phone, String code, String templateCode);

    void sendSms(String phone, List<SmsData> smsDataList, String templateCode);

}
