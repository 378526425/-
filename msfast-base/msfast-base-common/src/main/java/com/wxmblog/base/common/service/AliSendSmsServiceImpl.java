package com.wxmblog.base.common.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import com.google.common.collect.Lists;
import com.wxmblog.base.common.config.AliSmsConfig;
import com.wxmblog.base.common.enums.AliMsgErrCode;
import com.wxmblog.base.common.enums.BaseExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.rest.request.sms.SmsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-23 16:12
 **/

@Service
public class AliSendSmsServiceImpl implements ISendSmsService {

    @Autowired
    AliSmsConfig aliSmsConfig;

    /**
     * @param phone
     * @param code
     * @param templateCode
     * @Description: 发送短信
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/23 下午4:31
     */
    @Override
    public void sendSms(String phone, String code, String templateCode) {

        sendSms(phone, Lists.newArrayList(
                new SmsData("code", code)
        ), templateCode);

    }

    @Override
    public void sendSms(String phone, List<SmsData> smsDataList, String templateCode) {

        String regex = "^[0-9]{11}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            throw new JrsfException(BaseExceptionEnum.PHONE_FORMAT_ERROR);
        }

        String regionId = "cn-hangzhou";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, aliSmsConfig.getAccessKeyId(), aliSmsConfig.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", aliSmsConfig.getSignName());
        request.putQueryParameter("TemplateCode", templateCode);

        List<String> dataList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(smsDataList)) {
            smsDataList.forEach(model -> {
                dataList.add("\"" + model.getName() + "\": \"" + model.getValue() + "\"");
            });
        }

        request.putQueryParameter("TemplateParam", "{" + dataList.stream().collect(Collectors.joining(",")) + "}");
        CommonResponse response = null;

        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            throw new JrsfException(BaseExceptionEnum.SMS_EXCEPTION_EXCEPTION).setMsg(e.getErrMsg());
        }
        if (response == null)
            throw new JrsfException(BaseExceptionEnum.SMS_EXCEPTION_EXCEPTION);
        JSONObject jsonObject = JSONObject.parseObject(response.getData());
        String resultCode = jsonObject.getString("Code");
        if (!"OK".equalsIgnoreCase(resultCode)) {
            AliMsgErrCode[] aliMsgErrCodes = AliMsgErrCode.values();
            for (AliMsgErrCode aliMsgErrCode : aliMsgErrCodes) {
                if (aliMsgErrCode.getMsg().equalsIgnoreCase(resultCode)) {
                    throw new JrsfException(BaseExceptionEnum.SMS_EXCEPTION_EXCEPTION).setMsg(aliMsgErrCode.name());
                }
            }
            throw new JrsfException(BaseExceptionEnum.SMS_EXCEPTION_EXCEPTION).setMsg(jsonObject.getString("Message"));
        }
    }


}
