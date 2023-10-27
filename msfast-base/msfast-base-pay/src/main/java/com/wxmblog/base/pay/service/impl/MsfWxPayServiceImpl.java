package com.wxmblog.base.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.wxmblog.base.common.constant.ConfigConstants;
import com.wxmblog.base.common.enums.BaseExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.utils.MsfCommonTool;
import com.wxmblog.base.common.utils.SM4Util;
import com.wxmblog.base.common.utils.SpringUtils;
import com.wxmblog.base.pay.common.rest.request.OrderSubmitRequest;
import com.wxmblog.base.pay.config.MsfWXConfig;
import com.wxmblog.base.pay.service.IWxPayService;
import com.wxmblog.base.pay.service.MsfWxPayService;
import com.wxmblog.base.pay.common.rest.response.NotifyUrlData;
import com.wxmblog.base.pay.common.rest.response.PayOrderData;
import com.wxmblog.base.pay.utils.wx.sdk.WxNotifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RefreshScope
@Service
@Slf4j
public class MsfWxPayServiceImpl implements MsfWxPayService {

    private static final String wxAppletHost = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Map<String, String> wxAppletPay(OrderSubmitRequest request) throws Exception {

        String openId = null;
        if (StringUtils.isNotBlank(request.getCode())) {
            String appId = ConfigConstants.WX_APPLET_APPID();
            String secret = ConfigConstants.WX_APPLET_SECRET();
            String result = restTemplate.getForObject(wxAppletHost + "?appid=" + appId + "&secret=" + secret + "&js_code=" + request.getCode() + "&grant_type=authorization_code", String.class);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer errcode = jsonObject.getInteger("errcode");
            if (errcode == null) {
                openId = jsonObject.getString("openid");
            } else {
                throw new JrsfException(BaseExceptionEnum.API_ERROR).setMsg(jsonObject.getString("errmsg"));
            }
        }

        IWxPayService iWxPayService = SpringUtils.getBean(IWxPayService.class);
        PayOrderData payOrderData = iWxPayService.wxAppletPay(request);

        MsfWXConfig config = new MsfWXConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();

        data.put("body", payOrderData.getBody());
        data.put("out_trade_no", payOrderData.getOutTradeNo());

        data.put("total_fee", String.valueOf(payOrderData.getTotalFee()));
        data.put("spbill_create_ip", MsfCommonTool.getIpAddress());
        data.put("notify_url", ConfigConstants.PAY_WX_APPLET_NOTIFY_URL());
        data.put("trade_type", "JSAPI");
        data.put("openid", openId);

        //加密
        if (StringUtils.isNotBlank(payOrderData.getAttach())) {
            data.put("attach", SM4Util.encryptHex(payOrderData.getAttach()));
        } else {
            data.put("attach", SM4Util.encryptHex(payOrderData.getOutTradeNo()));
        }
        Map<String, String> resp = wxpay.unifiedOrder(data);
        if (StringUtils.isNotBlank(resp.get("prepay_id"))) {
            String prepay_id = resp.get("prepay_id"); //预支付id
            Map<String, String> payMap = new HashMap<String, String>();
            payMap.put("appId", ConfigConstants.WX_APPLET_APPID());
            payMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            String paySign = WXPayUtil.generateSignature(payMap, config.getKey());
            payMap.put("paySign", paySign);
            return payMap;
        }
        return null;
    }

    @Override
    public String wxPayNotifyUrl(HttpServletRequest request, HttpServletResponse response, String platform) {

        log.info("进入支付回调");
        //System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
        InputStream is = null;
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = WxNotifyUtil.inputStream2String(is, "UTF-8");
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
            log.info("回调微信数据{}", JSONObject.toJSONString(notifyMap));
            if (notifyMap.get("return_code").equals("SUCCESS")) {
                if (notifyMap.get("result_code").equals("SUCCESS")) {
                    String outTradeNo = notifyMap.get("out_trade_no");//商户订单号
                    String attach = notifyMap.get("attach");
                    /*以下是自己的业务处理------仅做参考*/

                    NotifyUrlData notifyUrlData = new NotifyUrlData();
                    notifyUrlData.setOutTradeNo(outTradeNo);

                    if (StringUtils.isNotBlank(attach)) {
                        notifyUrlData.setAttach(SM4Util.decryptStr(attach));
                    }

                    IWxPayService iWxPayService = SpringUtils.getBean(IWxPayService.class);
                    if ("applet".equals(platform)) {
                        iWxPayService.appletNotifyUrl(notifyUrlData);
                    } else if ("public".equals(platform)) {
                        iWxPayService.publicNotifyUrl(notifyUrlData);
                    }

                }
            }

            //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> wxPublic(OrderSubmitRequest request) throws Exception {
        String openId = null;
        if (StringUtils.isNotBlank(request.getCode())) {
            String appId = ConfigConstants.WX_PUBLIC_APPID();
            String secret = ConfigConstants.WX_PUBLIC_SECRET();
            String result = restTemplate.getForObject(wxAppletHost + "?appid=" + appId + "&secret=" + secret + "&js_code=" + request.getCode() + "&grant_type=authorization_code", String.class);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer errcode = jsonObject.getInteger("errcode");
            if (errcode == null) {
                openId = jsonObject.getString("openid");
            } else {
                throw new JrsfException(BaseExceptionEnum.API_ERROR).setMsg(jsonObject.getString("errmsg"));
            }
        }

        IWxPayService iWxPayService = SpringUtils.getBean(IWxPayService.class);
        PayOrderData payOrderData = iWxPayService.wxPublicPay(request);

        MsfWXConfig config = new MsfWXConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();

        data.put("body", payOrderData.getBody());
        data.put("out_trade_no", payOrderData.getOutTradeNo());

        data.put("total_fee", String.valueOf(payOrderData.getTotalFee()));
        data.put("spbill_create_ip", MsfCommonTool.getIpAddress());
        data.put("notify_url", ConfigConstants.PAY_WX_PUBLIC_NOTIFY_URL());
        data.put("trade_type", "JSAPI");
        data.put("openid", openId);

        //加密
        if (StringUtils.isNotBlank(payOrderData.getAttach())) {
            data.put("attach", SM4Util.encryptHex(payOrderData.getAttach()));
        } else {
            data.put("attach", SM4Util.encryptHex(payOrderData.getOutTradeNo()));
        }
        Map<String, String> resp = wxpay.unifiedOrder(data);
        if (StringUtils.isNotBlank(resp.get("prepay_id"))) {
            String prepay_id = resp.get("prepay_id"); //预支付id
            Map<String, String> payMap = new HashMap<String, String>();
            payMap.put("appId", ConfigConstants.WX_PUBLIC_APPID());
            payMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            String paySign = WXPayUtil.generateSignature(payMap, config.getKey());
            payMap.put("paySign", paySign);
            return payMap;
        }
        return null;
    }
}

