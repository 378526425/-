package com.wxmblog.base.auth.authority.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.wxmblog.base.auth.common.rest.request.wx.WxTemplateMsgRequest;
import com.wxmblog.base.auth.common.rest.response.DataValue;
import com.wxmblog.base.auth.common.rest.response.WxAppletOpenResponse;
import com.wxmblog.base.common.constant.ConfigConstants;
import com.wxmblog.base.common.enums.BaseExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-07 16:44
 **/

@Service
public class WxAppletServiceImpl implements WxAppletService {

    private static final String wxAppletHost = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public WxAppletOpenResponse getOpenIdInfoByCode(String code) {

        WxAppletOpenResponse response = new WxAppletOpenResponse();
        //todo 测试数据需要删除
      /*  response.setOpenId("ofKw_4ylF-leswYTqtf3JD6VExHs");
        response.setSessionKey("dfg");
        response.setUnionId("hjk");
        if (true) {
            return response;
        }*/
        if (StringUtils.isNotBlank(code)) {
            String appId = ConfigConstants.WX_APPLET_APPID();
            String secret = ConfigConstants.WX_APPLET_SECRET();
            String result = restTemplate.getForObject(wxAppletHost + "?appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code", String.class);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer errcode = jsonObject.getInteger("errcode");
            if (errcode == null) {
                response.setOpenId(jsonObject.getString("openid"));
                response.setSessionKey(jsonObject.getString("session_key"));
                response.setUnionId(jsonObject.getString("unionid"));
            } else {
                throw new JrsfException(BaseExceptionEnum.API_ERROR).setMsg(jsonObject.getString("errmsg"));
            }
        }

        return response;
    }

    @Override
    public String getAccessToken() {

        String appId = ConfigConstants.WX_APPLET_APPID();
        String secret = ConfigConstants.WX_APPLET_SECRET();
        String result = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?appid=" + appId + "&secret=" + secret + "&grant_type=client_credential", String.class);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode == null) {
            return jsonObject.getString("access_token");
        } else {
            throw new JrsfException(BaseExceptionEnum.API_ERROR).setMsg(jsonObject.getString("errmsg"));
        }
    }

    @Override
    public void sendWxTemplateMessage(WxTemplateMsgRequest request) {

        String host = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
        Map param = new HashMap();

        param.put("template_id", request.getTemplateId());
        param.put("page", request.getPage());
        param.put("touser", request.getTouser());

        Map<String, DataValue> dataMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(request.getData())) {
            request.getData().forEach(model -> {
                dataMap.put(model.getName(), new DataValue(model.getValue()));
            });
        }
        param.put("data", dataMap);

        param.put("miniprogram_state", request.getMiniprogramState());
        param.put("lang", request.getLang());
        String result = restTemplate.postForObject(host, param, String.class);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode != null) {
            throw new JrsfException(BaseExceptionEnum.API_ERROR).setMsg(jsonObject.getString("errmsg"));
        }
    }
}
